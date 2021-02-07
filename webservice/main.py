#!/usr/bin/python3

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
import sys
import sqlite3


try:
    conn = sqlite3.connect("main.db",check_same_thread=False)
except Exception as e:
    print(str(e))
    sys.exit("DATABASE ERROR")

app = Flask(__name__)


def checkruid(ruuid):
    print(ruuid)
    c = conn.execute("SELECT * FROM users WHERE uuid=(?)",(ruuid,))
    a = 0
    for row in c:
        a += 1
    return (True if a> 0 else False)

@app.route('/message', methods=['GET'])
def message():
    key = request.args.get('key', default = '', type = str)
    name = request.args.get('name', default = '', type = str)

    if key != SHIFT_API_KEY:
        return jsonify(isError = True,
                       message = "wrong api key",
                       statusCode = 200)

    return jsonify(isError = False,
                   message = "Success",
                   data ='<html>Hello ' + name + ', welcome back.<br><br>Have a look at our website <a href="http://www.shifting.site">www.shifting.site</a> for news.</html>',
                   statusCode = 200)

@app.route('/register', methods=['GET'])
def register():
<<<<<<< HEAD
    key = request.args.get('key')
    name = request.args.get('name')
    uuid = request.args.get('uuid')
    ruuid = request.args.get('ruuid')
    print(ruuid)
=======
    key = content['key']
    name = content['name']
    uuid = content['uuid']
    ruuid = content['ruuid']

>>>>>>> 6f9facb79eaf879bc7d55956ed666c54723227aa
    if key != SHIFT_API_KEY:
        return jsonify(isError = True, message = "wrong api key", statusCode = 200)
    
    # todo
    if checkruid(ruuid):
        c = conn.execute("INSERT INTO users VALUES(?,?,?,?)",(uuid,ruuid,name,0))
        conn.commit()
    else:
        return jsonify(isError = True,message = "invalid ruuid",statusCode=200)
    # save record to db
    return jsonify(isError = False,
                   message = "Success",
                   statusCode = 200)

@app.route('/setscooping', methods=['POST'])
def scooping():
    content = request.json
    key = content['key']
    uuid = content['uuid']
    scooping = content['scooping']

    if key != SHIFT_API_KEY:
        return jsonify(isError = True, message = "wrong api key: ", statusCode = 200)

    # update account table, set scooping

    return jsonify(isError = False,
                   message = "Success",
                   statusCode = 200)

@app.route('/matelist', methods=['GET'])
def friendlist():
    key = request.args.get('key', default = '', type = str)
    uuid = request.args.get('uuid', default = '', type = str)

    if key != SHIFT_API_KEY:
        return jsonify(isError = True,
                       message = "wrong api key",
                       statusCode = 200)

    list = [
            {'uuid': 'bcd', 'name': 'Helga Hofmann', 'scooping': "12345676"},
            {'uuid': 'bcd', 'name': 'Bernd Hofmann', 'scooping': "12345678"},
            {'uuid': 'abc', 'name': 'Hans Meiser', 'scooping': "0"}
           ]
    return jsonify(isError = False,
                   message = "Success",
                   statusCode = 200, data = list)


app.run(debug=True)
