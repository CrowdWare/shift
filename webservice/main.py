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
