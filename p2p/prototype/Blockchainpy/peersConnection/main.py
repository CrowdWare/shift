import socket
import sys
from _thread import start_new_thread
import uuid

class Peer:
	def __init__(self,host,port):
		super(Peer,self).__init__()
		self.host = host
		self.port = port
		self.peers = dict()
		# Initialization Section
		try:
			self.initializePeer()
			start_new_thread(self.waitingForConnections())
		except KeyboardInterrupt:
			self.closeConnection()
	def initializePeer(self):
		try:
			self.s = socket.socket(socket.AF_INET,socket.SOCK_DGRAM) # Initialize main socket
			self.s.bind((self.host,self.port)) # Bind socket to host,port
			if self.s:
				return True
			else:
				return False
		except:
			return False

	def sendAmsg(self,addr,msg:str):
		try:
			self.s.sendto(msg.encode(),addr) # Send msg to address
			return True
		except:
			return False

	def waitingForConnections(self):
		while True:
			print("Waiting For Connection...")
			data,addr = self.s.recvfrom(4096) # Receive 4096 byte
			data = data.decode()
			if data == "0x610x640x64": # If data == "add" in hex (add to list)
				self.addToPeers(addr)
			elif data ==  "0x720x650x6D": # if data == "rem" in hex (remove from list)
				self.removePeer(addr)
			else:
				print(data)

	def addToPeers(self,addr):
		if not self.getKeyFromValue(self.peers,addr): # Check if the address is not in self.peers
			self.peers[str(uuid.uuid4())] = addr # Add it to self.peers with a key is a uuid4 string
			print("Adding "+addr)
			print("Current Peers "+self.peers)
			return True
		else: # Else return False
			return False
	def removePeer(self,addr):
		pos = self.getKeyFromValue(self.peers,addr) # Get the key of the addr in self.peers
		if not pos: # If it does not exist return False
			return False
		else: # If it does Delete the key and its value
			del self.peers[pos]
			print("Removing "+addr)
			print("Current Peers "+self.peers)

	def getKeyFromValue(self,dictionary,value):
		list_keys = list(dictionary.keys()) # Get the keys in a list
		list_vals = list(dictionary.values()) # Get the values in a list

		if value not in list_vals or value not in list_keys: # Another method to check if the value is founded or not
			return False
		else:
			try:
				pos = list_vals.index(value) # Search for index of value in values list
				return list_keys[pos] # Return the key using the index of the value 
			except ValueError: # If the value was not found return False
				return False

	def closeConnection(self):
		self.peers.clear()
		self.s.close()
		print("Peer is closed,Exiting...")
		
if __name__ == "__main__":
	peer = Peer(socket.gethostname(),43000)
	peer.initializePeer()