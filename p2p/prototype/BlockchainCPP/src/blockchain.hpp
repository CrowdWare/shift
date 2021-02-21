#include <iostream>
#include <ctime>

class Block{
	private:
		static long long int order;
	public:
		int size;
		char* hash;
		char* phash;
		std::time_t timestamp;
		void getDetails(){
			std::cout << "Hash : " << hash << " <|> Previous Hash : " << phash << " <|> Timestamp : " << timestamp << " <|> Block Hash Size : " << size << std::endl; 
		}
		Block(int,char*,char*,std::time_t);
};

Block::Block(int s,char* h,char* ph,std::time_t ts){
	this->size = s;
	this->hash = h;
	this->phash = ph;
	this->timestamp = ts;
}