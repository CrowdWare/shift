class Block:
    def __init__(self,version,sh,prev,data,amount,timestamp):
        self.prev = prev
        self.hash = sh
        self.data = data
        self.amount = amount
        self.timestamp = timestamp
        self.version = version
    def genesis(self,version,sh,data,amount,timestamp):
        self.hash = sh
        self.data = data
        self.amount = amount
        self.timestamp = timestamp
        self.version = version
