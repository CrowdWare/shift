#include <iostream>
#include "src/crypt.hpp"
#include "src/blockchain.hpp"
#include <ctime>

#define log(x) std::cout << x << std::endl;

int main(){
	
	char str[] = "Art Sent 10THX To Brian";
	char sdigest[33];
	int sizes = sizeof str;
	
	hash(str,sizes,sdigest);
	
	char genesisblockstr[] = "Do not go gentle into that good night , old age should burn and rave at close of day; Rage Rage against the dying of the light.";
	
	char genesisblockd[33]; // DIGEST
	int gbsize = sizeof genesisblockstr;
	
	hash(genesisblockstr,gbsize,genesisblockd);
	
	Block* bl = new Block(sizeof sdigest,sdigest,genesisblockd,std::time(0)); // (size , hash , prev_hash,timestamp)
	
	bl->getDetails();// Directly prints block data to stdout
	
	// Block Cannot be destroyed
	return 0;
}
