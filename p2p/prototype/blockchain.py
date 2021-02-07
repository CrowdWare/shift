class Block:
    def __init__(self,prev,sh,data,amount,timestamp):
        self.prev = prev
        self.hash = sh
        self.data = data
        self.amount = amount
        self.timestamp = timestamp
    def genesis(self,sh,data,amount,timestamp):
        self.hash = sh
        self.data = data
        self.amount = amount
        self.timestamp = timestamp
