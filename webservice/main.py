#############################################################################
# Copyright (C) 2023 CrowdWare
#
# This file is part of SHIFT.
#
#  SHIFT is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  (at your option) any later version.
#
#  SHIFT is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
#
#############################################################################

from datetime import datetime, timedelta
from flask import Flask
from flask import request
from flask import jsonify
from shift_keys import SHIFT_API_KEY
from shift_keys import SHIFT_DB_PWD
from shift_keys import SHIFT_DB_USER
from shift_keys import SHIFT_DATABASE
from shift_keys import SHIFT_SECRET_KEY
from mysql.connector import connect
from mysql.connector.errors import IntegrityError
from Crypto.Cipher import AES
import base64
import json
import binascii


## pip install pycryptodome

def dbConnect():
    db = connect(unix_socket="/var/run/mysqld/mysqld.sock",
                 user=SHIFT_DB_USER,
                 password=SHIFT_DB_PWD,
                 database=SHIFT_DATABASE)
    return db

def isScooping(scoop):
    scoop_date = datetime(1970, 1, 1) + timedelta(seconds=scoop)
    time_since = datetime.now() - scoop_date
    seconds = int(time_since.total_seconds())
    if (seconds / 60.0 / 60.0) > 20:
        return False
    return True

def getScoopTimeHoursAgo(hoursAgo=24):
    first_date = datetime(1970, 1, 1)
    time_since = datetime.now() - first_date
    seconds = int(time_since.total_seconds())
    seconds -= hoursAgo * 60 * 60
    return seconds

""" def decryptString(cipherText: str) -> str:
    cipherText = base64.b64decode(cipherText)
    cipher = AES.new(SHIFT_SECRET_KEY.encode('utf-8'), AES.MODE_CBC, bytearray(b'\x01\x23\x45\x67\x89\xab\xcd\xef\xfe\xdc\xba\x98\x76\x54\x32\x10'))
    plainText = cipher.decrypt(cipherText)
    return plainText.decode('latin-1')[:16] """

def decryptStringGCM(cipherText: str) -> str:
    data = binascii.unhexlify(cipherText)
    iv, tag = data[:12], data[-16:]
    cipher = AES.new(SHIFT_SECRET_KEY.encode('utf-8'), AES.MODE_GCM, iv)
    plaintext = cipher.decrypt_and_verify(data[12:-16], tag)
    return plaintext.decode("utf-8")

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello here is the webservice of Shift!'

@app.route('/message', methods=['POST'])
def message():
    content = request.json
    key = decryptStringGCM(content['key'])
    name = content['name']
    test = content["test"] # used only for unit testing

    if key != SHIFT_API_KEY:
        return jsonify(isError=True, message="wrong api key", statusCode=200)

    if test == "true":
        message = "Message from server"
    else:
        message = '<html>Hello ' + name + ', welcome back.<br><br>Have a look at our website <a href="http://shift.crowdware.at/">shift.crowdware.at/</a> for news.</html>'
    
    return jsonify(isError=False,
                   message="Success",
                   data=message,
                   statusCode=200)

@app.route('/register', methods=['POST'])
def register():
    content = request.json
    key = decryptStringGCM(content['key'])
    name = content['name']
    uuid = content['uuid']
    ruuid = content['ruuid']
    country = content['country']
    language = content['language']
    test = content["test"] # used only for unit testing

    if key != SHIFT_API_KEY:
        return jsonify(isError=True, message="wrong api key", statusCode=200)
    
    if test != "true":
        conn = None
        try:
            conn = dbConnect()
            if uuid != ruuid:
                curs = conn.cursor(dictionary=True)
                query = 'SELECT COUNT(*) AS count FROM account WHERE uuid = "' + ruuid + '"'
                curs.execute(query)
                row = curs.fetchone()
                count = row['count']
                if count != 1:
                    return jsonify(isError=True, message="The referer id is not correct.", statusCode=200)
            curs = conn.cursor()
            query = 'INSERT INTO account(name, uuid, ruuid, scooping, country, language) VALUES("' + name + '", "' + uuid + '", "' + ruuid + '", 0, "' + country + '","' + language +'")'
            curs.execute(query)
            conn.commit()
        except IntegrityError as error:
            return jsonify(isError=True, message=error.msg, statusCode=200)
        finally:
            if conn != None:
                conn.close()

    return jsonify(isError=False, message="Success", statusCode=200)

@app.route('/setscooping', methods=['POST'])
def scooping():
    content = request.json
    key = decryptStringGCM(content['key'])
    uuid = content['uuid']
    test = content["test"] # used only for unit testing

    if key != SHIFT_API_KEY:
        return jsonify(isError = True, message = "wrong api key: ", statusCode = 200)

    first_date = datetime(1970, 1, 1)
    time_since = datetime.now() - first_date
    seconds = int(time_since.total_seconds())
    level_1 = 0
    level_2 = 0
    level_3 = 0
    if test != "true":
        conn = None
        scoopTime24HoursAgo = getScoopTimeHoursAgo()

        try:
            conn = dbConnect()
            curs = conn.cursor()
            query = 'UPDATE account SET scooping = ' + str(seconds) + ' WHERE uuid = "' + uuid + '"'
            curs.execute(query)
            conn.commit()

            curs = conn.cursor(dictionary=True)
            query = 'SELECT COUNT(*) AS count FROM account WHERE ruuid = "' + uuid + '" AND scooping > ' + str(scoopTime24HoursAgo)
            curs.execute(query)
            row = curs.fetchone()
            level_1 = row['count']

            curs = conn.cursor(dictionary=True)
            query = 'SELECT COUNT(*) AS count FROM account WHERE ruuid IN (SELECT uuid FROM account WHERE ruuid = "' + uuid + '") AND scooping > ' + str(scoopTime24HoursAgo)
            curs.execute(query)
            row = curs.fetchone()
            level_2 = row['count']

            curs = conn.cursor(dictionary=True)
            query = 'SELECT COUNT(*) AS count FROM account WHERE ruuid IN (SELECT uuid FROM account WHERE ruuid IN (SELECT uuid FROM account WHERE ruuid = "' + uuid + '")) AND scooping >' + str(scoopTime24HoursAgo)
            curs.execute(query)
            row = curs.fetchone()
            level_3 = row['count']

        except IntegrityError as error:
            return jsonify(isError=True, message=error.msg, statusCode=200)
        finally:
            if conn != None:
                conn.close()

    return jsonify(isError = False,
                   message = "Success",
                   statusCode = 200, 
                   count_1 = level_1,
                   count_2 = level_2,
                   count_3 = level_3)

@app.route('/matelist', methods=['POST'])
def friendlist():
    content = request.json
    key = decryptStringGCM(content['key'])
    uuid = content['uuid']
    test = content["test"] # used only for unit testing

    if key != SHIFT_API_KEY:
        return jsonify(isError=True,
                       message="wrong api key",
                       statusCode=200)
    
    accounts = []
    if test == "true":
        not_scooping = int((datetime.now() - timedelta(seconds=72001) - datetime(1970, 1, 1)).total_seconds())
        scooping = int((datetime.now() - timedelta(seconds=71999) - datetime(1970, 1, 1)).total_seconds())
        accounts.append({'uuid' : '1234567890', 'name' : 'Testuser 1', 'country' : 'England', 'scooping' : isScooping(0)})
        accounts.append({'uuid' : '1234567891', 'name' : 'Testuser 2', 'country' : 'Germany' ,'scooping' : isScooping(not_scooping)})
        accounts.append({'uuid' : '1234567892', 'name' : 'Testuser 3', 'country' : 'Brasil' , 'scooping' : isScooping(scooping)})
    else:
        conn = None
        try:
            conn = dbConnect()
            curs = conn.cursor(dictionary=True)
            query = 'SELECT uuid, name, country, scooping, (SELECT COUNT(*) FROM account WHERE ruuid = a.uuid) AS friends_count FROM account a WHERE ruuid  = "' + uuid + '" and uuid <> "' + uuid + '" ORDER BY friends_count desc, scooping desc'
            curs.execute(query)
            for row in curs:
                accounts.append({'uuid' : row['uuid'], 'name' : row['name'], 'country' : row['country'], 'scooping' : isScooping(row['scooping']), 'friends_count' : row['friends_count']})
        except IntegrityError as error:
            return jsonify(isError=True, message=error.msg, statusCode=200)
        finally:
            if conn != None:
                conn.close()

    return jsonify(isError=False,
                   message="Success",
                   statusCode=200, data=accounts)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
