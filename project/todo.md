# ToDo

# Version 1
- It was a mistake t use QT, because even me could not bring QT to deploy to Android (it was only possible on Linux a few years ago)

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
- Theme chooser
+ Scan proposal, unmarshal transaction, fraud detection 
+ Book transaction to account
+ Subtract demurrage from GetBalance
+ Make QR-Code save against replay attack
+ Balance display should online display balance without scooped when in liter mode
+ Settings change name
- Setting language should select actual language
+ Filechooser is kinda broken (black screen). Just add APKs from Download directory for installation.
+ Balance display should switch between balance and scooping and should display the hours left for next scooping.
- Splashscreen is looking awful
+ If last step of transaction is overstepped, we need a possibilty to reshow the QR-Code. (click transaction and show QR)
- Invitecode edit if user has not yet join correctly
- Display message when register account has not been performed because invite was wrong.
- Instead of or additional show transaction data in the dialog.
- Have a look at StartScooping !!!

  
# Beta 2.0
- Color for drawer select background should be blueish, general theme check
- Display value can be cached...only load daily blocks from shift.db 
- Multiple themes to choose from (I personally like orange on dark grey background)
- Request focus when dragging the circular picker
- Avoid replay attacks on webservice
- create an easy to use API (see API requirements)


# App 3.0
- Key exchange via text or QR-code or TOFU
- Chat function based on Storj for async messages (have a look at Telegram for voice chat and calls) 
- Posts for micro blogging
- Additional to hours and minutes we should be able to enter days, weeks, months. 
- All webservice calls errors ignored, if WebService is offline(in case someone wants to disturb us with DOS attacks on the server)
- In app purchase or ability to buy LMC for companies, so that they can pay employees


# Web
- Introduction to animate people to join (video)
- Video showing the uses cases of the app
- Book showing how to build a plugin 
+ New screen print of the app
+ Translate to Esperanto, French, Spanish, Portuguese
- Tutorial to create plugins
- Tutorial to install the app via APK download
- Tutorial to install a plugin 


# Desktop App
- A desktop app can be used to transfer liquid from mobile to desktop to be able to save the liquid (to save liquid we could also use our tablet or a friends mobile)
- It could also be used as DHT node to store posts and messages for chat and blogging


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


# QR code
QR codes print as coupon on a desktop app
Scan function to check if QR code is valid
The QR code will present an address on Storj encrypt(AccessKey, Bucketname, key).
The value of the record is the encrypted transaction.
After reading this record it will be deleted and then booked into account.
This avoid double spending.
This record also has a demurrage.
The Storj-bucket belongs to the user. So each user has its own, up to 25 GB free bucket. 

# Desktop app for business
When a company want to pay their employees they need a big amounts of LMC.
So they can buy a QR code to be scanned. Lets create QR codes and sell them against dollars or euros.
That will pay the development cost and CrowdWare can use this fiat money to invest into sustainable projects like creating UBUNTU villages.

# POS
Point of sale app


# QC-Code
Avoid double spending: 
Proposal transaction has UUID from receiver and agreement transaction also has this UUID, so only the receiver can use the agreement transaction.

Avoid replay attacks:
When an agreement transaction is booked we have to make sure, that this transaction has not been used before. This can be archived because of the timestamp. Two transactions from the same app can not have the same timestamp.

# Paid version
Let us think about the release of a paid version to collect development cost.
What about a free version with 1 l initial liquid.
A paid version for 1,- with 10 l initial liquid.
A paid version for companies for 100,- with 200 l initial liquid.

Sell on Elopage

# Storage
Each user is able to allocate up to 25 GB free space on Storj. There the user can create a bucket, store posts, messages, files etc and can share these with friends. In the app the users enters the access key and the bucket name