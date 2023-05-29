# ToDo

# Alpha 2.0
+ Invite friends
+ Read and display last x transactions
+ Page to display mates
+ Scooping algorythm (minted balance)
+ Start scooping webcall
+ Fontsize of balance should be autosize, one should toggle the display between liter and ml
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
    booking after a full liter is scooped (timestamp for demurage)
    in the bookings list values are cumulated per day 
+ Book all transactions via trustchain, before releasing (no need to drop db, when adding payments)
+ Start service when push start scooping
+ Give warning when scooping that service is running and wallet will be dropped after uninstall
+ Plugin sample
+ We need a better hour and minute picker design
+ Writing a block after day change should use the date from the day before. 
+ Balance Display state should be saved in prefs or/and it should switch to liter when amount > X
+ Transaction should only store full liters.
+ Payment scan and send functionionality
+ We have unwanted blocks in the blockchain like message=pak, we might need a different service ID to form a separate trustchain 
+ Also think of an own discovery overlay, for above reason (or just change the existing) 
+ Transaction amount should only be in full liter, we cannot pay for half a minute or the like. In case of scooping we should round amount down.
- Instead of entering a key onjoin, qr code scan would be easier. This is also true for inviting friends. Put a trailing button to scan qr code. 
+ Drawer is visible on Tablet
+ Release build has some dependencies to libsodium, so build all submodules as signed apk
+ Input for minutes should stop at 55 and not wrap to zero (bad dragging behaviour). Hours should stop at 11.
  
# Beta 2.0
- Color for drawer select background should be blueish, general theme check
- isScooping should only be true if connected to the internet and not in flightmode
- bugfix: see crashlog
- The balance on the display is less than the amount in the blocks + in shift.db
- Display value from blocks can be cached...only load daily blocks from shift.db 
- Stromverbrauchswarnung from Android, how to avoid this?
- Multiple themes to choose from (I personally like orange on dark gray background)
- Request focus when dragging the cirlular picker
- Why does service crashes also when app crashes? service should run independently
- settings, language survives recreate
- Balance display should only display booked blocks when in liter mode (needed for giving, not to spent liquid from nonexisting/theoretically scooped blocks), ? maybe it should only display persistent transactions when in milli mode
- Avoid replay attacks on webservice

- create an easy to use API (see API requirements)


# App 3.0
- Key exchange via text or QR-code or TOFU
- Chat function based on DHT for async messages (have a look at Telegram for voicechat and calls) 
- DHT is already implemented in IPv8 for Python 
- Posts for micro blogging
- Additional to hours and minutes we should be able to enter days, weeks, months. 
- All webservice calls errors ignored, if WebService is offline(in case someone wants to dissturb us with DOS attacks on the server)


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
Therefore when request to recieve LMC the QR code with (Amount, purpose, name) from the receiver comes encrypted.
If the receiver is the attacker, then the giver will find out, because he cannot decrypt and read the QR code.  

In case the giver is the attacker, this will be rejected from receiver when transaction can not be decrypted.

Additionally we need key rotation, so that when a key expires, a new key will be used on all devices.


# Desktop App
- A desktop app can be used to transfer liquid from mobile to desktop to be able to save the liquid (to save liquid we could also use our tablet or a friends mobile)
- It could also be used as DHT node to store posts and messages for chat and blogging

# P2P
- https://github.com/Tribler/trustchain-superapp
- https://github.com/Tribler/kotlin-ipv8
- https://github.com/Tribler/py-ipv8


# Additional use cases for the app
https://github.com/imartinez/privateGPT
Lets train our model with real facts without the influence of mainstream media aka paid journalism.
The database must be hosted on a server in this case?

# Data usage
Scooping:   300 B * 365 days = 109.5 KB / year
Proposal:   300 B
Aggreement: 260 B

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


# Demurage
The demurage of the liquid per day is 0.27% so that an amount of 1000 ml is worth below 1.0 after 7 years (2555 days)


