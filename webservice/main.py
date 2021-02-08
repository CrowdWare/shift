#############################################################################
# Copyright (C) 2021 CrowdWare
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

from flask import Flask
from flask import request
from flask import jsonify
from shift_keys import SHIFT_API_KEY
from shift_keys import SHIFT_DB_PWD
from shift_keys import SHIFT_DB_HOST
from shift_keys import SHIFT_DB_USER
from shift_keys import SHIFT_DATABASE
from mysql.connector import connect
from mysql.connector.errors import IntegrityError


def dbConnect():
    db = connect(host=SHIFT_DB_HOST,
                 user=SHIFT_DB_USER,
                 password=SHIFT_DB_PWD,
                 database=SHIFT_DATABASE)
    return db

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello here is the webservice of Shift!'

@app.route('/message', methods=['POST'])
def message():
    content = request.json
    key = content['key']
    name = content['name']

    if key != SHIFT_API_KEY:
        return jsonify(isError=True, message="wrong api key", statusCode=200)

    return jsonify(isError = False,
                   message = "Success",
                   data ='<html>Hello ' + name + ', welcome back.<br><br>Have a look at our website <a href="http://www.shifting.site">www.shifting.site</a> for news.</html>',
                   statusCode = 200)

@app.route('/register', methods=['POST'])
def register():
    content = request.json
    key = content['key']
    name = content['name']
    uuid = content['uuid']
    ruuid = content['ruuid']

    if key != SHIFT_API_KEY:
        return jsonify(isError=True, message="wrong api key", statusCode=200)

    try:
        conn = dbConnect()
        curs = conn.cursor()
        query = 'INSERT INTO account(name, uuid, ruuid, scooping) VALUES("' + name + '", "' + uuid + '", "' + ruuid + '", 0)'
        curs.execute(query)
        conn.commit()
    except IntegrityError as error:
        return jsonify(isError=True, message=error.msg, statusCode=200)
    finally:
        conn.close()

    return jsonify(isError=False, message="Success", statusCode=200)

@app.route('/setscooping', methods=['POST'])
def scooping():
    content = request.json
    key = content['key']
    uuid = content['uuid']
    scooping = content['scooping']

    if key != SHIFT_API_KEY:
        return jsonify(isError = True, message = "wrong api key: ", statusCode = 200)

    try:
        conn = dbConnect()
        curs = conn.cursor()
        query = 'UPDATE account SET scooping = ' + scooping + ' WHERE uuid = "' + uuid + '"'
        curs.execute(query)
        conn.commit()
    except IntegrityError as error:
        return jsonify(isError=True, message=error.msg, statusCode=200)
    finally:
        conn.close()

    return jsonify(isError = False,
                   message = "Success",
                   statusCode = 200)

@app.route('/matelist', methods=['POST'])
def friendlist():
    content = request.json
    key = content['key']
    uuid = content['uuid']

    if key != SHIFT_API_KEY:
        return jsonify(isError=True,
                       message="wrong api key",
                       statusCode=200)
    
    accounts = []
    try:
        conn = dbConnect()
        curs = conn.cursor(dictionary=True)
        query = 'SELECT uuid, name, CONVERT(scooping, char) AS scooping FROM account WHERE ruuid = "' + uuid + '" and uuid <> "' + uuid + '" ORDER BY name, scooping'
        curs.execute(query)
        for row in curs:
            accounts.append({'uuid' : row['uuid'], 'name' : row['name'], 'scooping' : row['scooping']})
    except IntegrityError as error:
        return jsonify(isError=True, message=error.msg, statusCode=200)
    finally:
        conn.close()

    return jsonify(isError=False,
                   message="Success",
                   statusCode=200, data=accounts)
