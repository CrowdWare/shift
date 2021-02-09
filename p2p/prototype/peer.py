import socket
import sys
from _thread import start_new_thread
import time

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
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) # Avoid 'Address Already in use' Exception
        # Load Server
        self.sock.bind((host,port))
        
        # Main Loop
        while True:
            r = self.sock.recvfrom(4096)
            msg = str(r[0].decode())
            sender = str(r[1][0])+":"+str(r[1][1])
            print(f"{sender} >> {msg}\n")
            self.sock.close()
            break

class Client:
    def __init__(self,host,port):
        self.host = host
        self.port = port
        self.sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        msg = input(f"{socket.gethostname()}:{port} >> ")
        self.sock.sendto(msg.encode(),(self.host,self.port))
        self.sock.close()
    
if __name__ == "__main__":
    try:
        if sys.argv[1] == "s":
            print("\rServer Starting...",flush=True)
            server = Server(socket.gethostname(),10000)
        elif sys.argv[1] == "c":
            print("\rClient starting...",flush=True)
            client = Client(socket.gethostname(),10000)
        else:
            sys.exit("peer.py [s/c] ; s=server,c=client")
    except Exception as e:
        print(str(e))
        sys.exit("peer.py [s/c] ; s=server,c=client")
