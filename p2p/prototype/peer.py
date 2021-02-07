import socket
import sys
from _thread import start_new_thread

class Verifier:
    def __init__(self,sh,prev):
        pass

class Server:
    def __init__(self,host,port):
        self.host = host
        self.port = port
        # Socket Object
        self.sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        # Socket configuration
        self.sock.set_blocking(0)  # Non-Blocking Mode
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) # Avoid 'Address Already in use' Exception
        # Load Server
        self.sock.bind((host,port))
        # Main Loop
        while True:
            pass

class Client:
    def __init__(self,host,port):
        self.host = host
        self.port = port
        self.sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        self.sock.set_blocking(0)
