#############################################################################
# Copyright (C) 2021 Olaf Japp
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

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello here is the webservice of Shift!'

@app.route('/message', methods=['GET'])
def message():
    name = request.args.get('name', default = '', type = str)
    return "Hello " + name + ", welcome back"

@app.route('/register', methods=['POST'])
def register():
    key = request.form.get('key')
    name = request.form.get('name')
    uuid = request.form.get('uuid')
    ruuid = request.form.get('ruuid')

    # todo
    # save record to db
    return jsonify(isError = False, message = "Success", statusCode = 200)

@app.route('/friendlist', methods=['GET'])
def friendlist():
    key = request.form.get('key')
    uuid = request.form.get('uuid')

    list = [
            {'uuid': 'abc', 'name': 'Hans Meiser', 'scooping': "0"},
            {'uuid': 'bcd', 'name': 'Bern Hofmann', 'scooping': "12345678"}
           ]
    return jsonify(isError = False, message = "Success", statusCode = 200, data = list)
