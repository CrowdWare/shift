# ToDo

# App 2.0
+ Invite friends
+ Read and display last x transactions
+ Page to display mates
+ Scooping algorythm (minted balance)
+ Start scooping webcall
+ Fontsize of balance should be autosize, one should toggle the display between liter and ml
+ Embed some Esperanto
+ Localize app
- Color for drawer select background should be blueish, general theme check
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
- Book all transactions via trustchain, before releasing (no need to drop db, when adding payments)
- Problem if user is offline and cannot let sign blocks (maybe scoop the whole day, and create a block once a day when online)
- Start service when push start scooping
- Give warning when scooping that service is running and wallet will be dropped after uninstall

# App 3.0
- Pay function using IPv8 trustchain
- Chat function based on DHT for async messages (have a look at Telegram for voicechat and calls) 
- DHT is already implemented in IPv8 for Python 
- Posts for micro blogging
  

# Web
- Introduction to animate people to join (video)
+ New screen print of the app
+ Translate to Esperanto, French, Spanish, Portuguese

# Webservice
+ Make it possible to have multiple level of referers

# Project
+ Involve stakeholder to get active

# Desktop App
- A desktop app can be used to transfer liquid from mobile to desktop to be able to save the data.
- It could also be used as DHT node to store posts and messages for chat and blogging


# P2P
- https://github.com/Tribler/trustchain-superapp
- https://github.com/Tribler/kotlin-ipv8
- https://github.com/Tribler/py-ipv8



# Problem to solve
To keep the database small scooping blocks should only be stored once a day and chached in the account file


# Data usage
Proposal: 300 B * 24h * 3 = 21.6 KB / Day * 365 days = 7.8 MB (better to book once a day)
Aggreement: 260 B