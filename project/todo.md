# ToDo

# Alpha 2.0
+ Invite friends
+ Read and display last x transactions
+ Page to display mates
+ Scooping algorithm (minted balance)
+ Start scooping web call
+ Font size of balance should be autosize, one should toggle the display between litre and ml
+ Embed some Esperanto
+ Localize app
+ For benefits only count mates that were scooping the last 24h
+ The icon should be larger on the splash screen, maybe we show something with a water drop or other liquids
+ Drawer shall look smarter with logo and the name of the user, (C) CrowdWare
+ after scooping finished reload bookings
+ Push notifications (establish a service with a P2P network) then we can broadcast messages
+ Warning that user will loose account uninstalling the app.
+ Secure socket layer to encrypt data or packet encryption 
+ Only scoop while service is running in background (that keeps the network active)
    scooping gives 7 ml/min + benefits for scooping friends
    booking after a full litre is scooped (timestamp for demurrage)
    in the bookings list values are cumulated per day 
+ Book all transactions via trustchain, before releasing (no need to drop db, when adding payments)
+ Start service when push start scooping
+ Give warning when scooping that service is running and wallet will be dropped after uninstall
+ Plugin sample
+ We need a better hour and minute picker design
+ Writing a block after day change should use the date from the day before. 
+ Balance Display state should be saved in prefs or/and it should switch to litre when amount > X
+ Transaction should only store full litres.
+ Payment scan and send functionality
+ We have unwanted blocks in the blockchain like message=pak, we might need a different service ID to form a separate trustchain 
+ Also think of an own discovery overlay, for above reason (or just change the existing) 
+ Transaction amount should only be in full litre, we cannot pay for half a minute or the like. In case of scooping we should round amount down.
- Instead of entering a key on join, qr code scan would be easier. This is also true for inviting friends. Put a trailing button to scan qr code. 
+ Drawer is visible on Tablet
+ Release build has some dependencies to libsodium, so build all submodules as signed apk
+ Input for minutes should stop at 55 and not wrap to zero (bad dragging behaviour). Hours should stop at 11.
+ Bevor starting the service, check if its already running to avoid crashes
  
# Beta 2.0
- Color for drawer select background should be blueish, general theme check
- isScooping should only be true if connected to the internet and not in flight mode
- The balance on the display is less than the amount in the blocks + in shift.db
- Display value from blocks can be cached...only load daily blocks from shift.db 
- Stromverbrauchswarnung from Android, how to avoid this?
- Multiple themes to choose from (I personally like orange on dark grey background)
- Request focus when dragging the circular picker
- Why does service crashes also when app crashes? service should run independently
- settings, language survives recreate
- Balance display should only display booked blocks when in litre mode (needed for giving, not to spent liquid from non-existing/theoretically scooped blocks), ? maybe it should only display persistent transactions when in milli mode
- Avoid replay attacks on webservice
- create an easy to use API (see API requirements)


# App 3.0
- Key exchange via text or QR-code or TOFU
- Chat function based on DHT for async messages (have a look at Telegram for voice chat and calls) 
- DHT is already implemented in IPv8 for Python 
- Posts for micro blogging
- Additional to hours and minutes we should be able to enter days, weeks, months. 
- All webservice calls errors ignored, if WebService is offline(in case someone wants to disturb us with DOS attacks on the server)


# API Requirements
- Library to use in plugins (also a place for the interface ShiftPlugin)
+ Gratitude function
- Change title in the appbar
- Get Colors from theme for corporate identity


# Plugin Ideas
- 13 moons calendar (https://www.lawoftime.org/thirteenmoon/tutorial.html)
- Open houses, map of open houses to occupy
- Newspaper with only positive messages

# Project
- Should the name say something like TIME instead of LIQUID?  LUNA GRATIA FLUO (LGF), LUNA GRATIA LIQUIDO (LGL)
- Find someone who starts the app and continues the development
- Or, start the app alone and be sure that the main app can stay unmodified (errorless)

# Web
- Introduction to animate people to join (video)
- Video showing the uses cases of the app
- Book showing how to build a plugin 
+ New screen print of the app
+ Translate to Esperanto, French, Spanish, Portuguese
- Tutorial to create plugins
- Tutorial to install the app via APK download
- Tutorial to install a plugin 

# Security
Maybe also use trust on first use (TOFU) for messaging and sign the contact as TOFU_CONTACT.

## Transaction
We need to make sure that only original apps can make transactions.
Therefore when request to receive LMC the QR code with (Amount, purpose, name) from the receiver comes encrypted.
If the receiver is the attacker, then the giver will find out, because he cannot decrypt and read the QR code.  

In case the giver is the attacker, this will be rejected from receiver when transaction can not be decrypted.

Additionally we need key rotation, so that when a key expires, a new key will be used on all devices.


# Desktop App
- A desktop app can be used to transfer liquid from mobile to desktop to be able to save the liquid (to save liquid we could also use our tablet or a friends mobile)
- It could also be used as DHT node to store posts and messages for chat and blogging

# P2P
- https://github.com/Tribler/trustchain-superapp
- https://github.com/Tribler/kotlin-ipv8
- https://github.com/MattSkala/kotlin-ipv8
- https://github.com/Tribler/py-ipv8


# Additional use cases for the app
https://github.com/imartinez/privateGPT
Lets train our model with real facts without the influence of mainstream media aka paid journalism.
The database must be hosted on a server in this case?

# Data usage
Scooping:   300 B * 365 days = 109.5 KB / year
Proposal:   300 B
Agreement: 260 B

# Promotion
- https://www.producthunt.com/
- https://www.reddit.com/
- https://news.ycombinator.com/
- https://www.willmcgugan.com/blog/tech/post/promoting-your-open-source-project-or-how-to-get-your-first-1k-github-stars/
- https://hambacherfest1832.blog/ueber/kontakt/  Dr. Wolfgang Kochanek

# Book
Write a book about this project, so that someone else is able to continue my work.
Topics
- Why do we need our own currency at all?
- Why wouldn't we need this app at all?
- How to build the app
- How to bootstrap the app
- How to build multiple circles of trust and combine them later.
- How to install plugins
- How to build plugins
- How to use the payments API 


# Demurrage
The demurrage of the liquid per day is 0.27% so that an amount of 1000 ml is worth below 1.0 after 7 years (2555 days)


# NEW
Rename LMC to NRG and the payment from LMP to NRG exchange.
Also scooping will be replaced with energy mining. NRG MINING
Change corporate identity to the theme of NRG (https://crowdware.github.io/nrg/index-en.html)
Green on dark grey.


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

