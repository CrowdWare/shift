#include <iostream>
#include <openssl/md5.h>
#include <stdio.h>
#include <string.h>

void hash(char* st,int size,char* ms);

void hash(char* st,int size,char* ms){

		unsigned char digest[MD5_DIGEST_LENGTH];

		char str[size] = {*st};

		MD5((unsigned char*)&str,strlen(str),(unsigned char*)&digest);		

		for (int i =0;i<16;i++){
				sprintf(&ms[i*2],"%02x",(unsigned int)digest[i]);
		}
}