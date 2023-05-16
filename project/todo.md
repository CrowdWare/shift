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
- Only scoop while service is running in background (that keeps the network active)
    scooping gives 7 ml/min + benefits for scooping friends
    booking after a full liter is scooped (timestamp for demurage)
    in the bookings list values are cumulated per day 

# App 3.0
- Pay function using IPv8 trustchain
- Chat function based on DHT for async messages (have a look at Telegram for voicechat and calls) 
- DHT is already implemented in IPv8 for Python 
- Posts for micro blogging
  

# Web
- Introduction to animate people to join (video)
+ New screen print of the app
- Translate to Esperanto, French, Spanish, Portuguese

# Webservice
+ Make it possible to have multiple level of referers

# Project
- Involve stakeholder to get active

# Desktop App
- A desktop app can be used to transfer liquid from mobile to desktop to be able to save the data.


# Push notification
Instead of using Google Firebase or OneSignal, which is based on Firebase, we should implement our own push service.
I am thinking of a P2P solution, where users can chat with each other (we need that anyways later). This way we can also broadcast messages to the users.
We will use IPv8 for this purpose.



# P2P
- https://github.com/Tribler/trustchain-superapp
- https://github.com/Tribler/kotlin-ipv8
- https://github.com/Tribler/py-ipv8