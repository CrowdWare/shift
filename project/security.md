# Scooping Security
When we started we thought that its save enough to only make transactions with other apps where we encrypt a transaction with a key only known by the original app. Then we knew that their scooping was legit.  
Because of the fact that an attacker could find out the encryption key, because its stored somewhere in the APK file, we have to rethink this strategy. So we can't use symmetric encryption, its not safe enough!  

Instead we will use a consensus algorithms before storing a scoop or initial block into the trustchain. 
Its all about trust.
If agent A is adding agent B to his friend list, then A would accept scooped blocks from B, because he trust him.
The same is true for B. B also trusts A and would sign scooped blocks from A.
Then B connects to C. Because of the fact that B is trusting C also A can trust C.
When I speak of trust here, I mean they have met in real life and have exchanged there public keys via QR code scan.
We already have got a circle of trust here.
If a member of the circle scoops a coin then all of the direct friends could sign the scooped block automatically.

In case an attacker wants to scoop a coin none of the members of the circle will sign this block, the app will not.  
Also non of the circle members would accept coins from an unknown source maybe the user would, but not the app.

To accept the app of other people we can use the following mechanisms. 
- The qr code which is used to invite a friend is encrypted and only if both apps are compatible then they can trust each other.
- Next step is to crawl the trustchain and verify that the genesis block has the specific amount (initial_amount).  
- Then we check the last x scoop blocks from the trustchain. They should not exceed a specific amount (getMaxGrow()).  
- Also we check the chain from the last block back to the last scooped block.
- Putting someone in the friendlist means, that we add a friend request into our trustchain. This friend request will contain a specific part of the friends trustchain so that we can verify later transactions. (Have a look at peerchat or the like, how they store friends) 

So at that point we have a list of friends that we can trust.  
When scooping a block we will send a proposal to all friends to sign it. If the block is signed by a friend, then it can be stored in the trustchain.  
If non of the friends are online then the transaction will be cached in the encrypted account data. On a regular base the proposal is send again.
Instead of the encrypted account data, we could use a second chain where we store intermediate scoop blocks, self signed. 
After day change we summarize the intermediate blocks and create one block for the main chain.
Then we can delete all intermediate blocks to save space.

Paying is a bit more tricky...   
We can pay directly if the receiver is in our friend list.  
If not, we can just exchange public keys and add each other.
If the receiver is not in the list and not there in person, then we just accept the transaction.  
We can trust the receiver, because he gave us value (a good or a service).
This is called TOFU (trust on first use).
We are not adding the other party automatically to our friend list.

From the receiver perspective it looks a bit different.  
The receiver has to crawl our trustchain like when exchanging keys. 


# Web Service
We needed the webservice to be able to know, who is referring whom.
When we switch to the circle o trust model then we don't need the web service anymore.  
We can just check via the network if a peer, a referred friend, is online and calculate the scouping value based on the amount of online friends.
So we don't need the join page anymore.  
The country and invitation code we neither need. When A wants to invite B, then A has to show an invitation QR code to B and B scans the code, then they exchange their public keys and store the new member in the database as friend. After scanning the public key we can give this friend a name.
Because we only trade with known people we neither need the from field in an LMP. We already have this name.

Before a user is able to start scooping he needs to add X friends which are able to verify the scooping. It doesn't matter if the friend is already in the circle or not. When adding a friend the app will check if the friend is already a user, if not this will count as an invitation and raises the scooped amount.

If a user finds out that a friend tries to attack the circle with an unknown app, then the friend can be manually removed from the friend list.

We have to remove the invite mechanism where we were able to invite unknown users.



