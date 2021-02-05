from flask import Flask
from flask import request

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello here is the webservice from Shift!'

@app.route('/message')
def message():
    name = request.args.get('name', default = '', type = str)
    return "Hello " + name + ", welcome back"
