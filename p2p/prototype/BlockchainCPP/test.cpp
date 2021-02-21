#include <iostream>
#include "src/crypt.hpp"
#include "src/blockchain.hpp"
#include <ctime>

int main(){
	// Init
	char str[] = "Do not do it";
	char dstr[33];
	int ssize = sizeof str;
	
	char prevhash[] = "Man";
	char prevhashd[33];
	int phsize = sizeof(prevhash);
	
	// Load Current Transaction Hash
	hash(str,ssize,dstr);
	// Load Previous Hash (Testing)
	hash(prevhash,phsize,prevhashd);
		
	Block* bl = new Block(sizeof dstr,dstr,prevhashd,std::time(0));

	bl->getDetails();
		
	
	return 0;
}
