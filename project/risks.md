# Risks

| Risk | Solution |
| :-- | :-- |
|User might not trust the app because a part is close source|They also trust Microsoft.<br>But maybe we can think about opening the source to distract hacker, wjen they see that its too hard to crack.|
| Big Brother can read feed | encrypt the feed |
| Big Brother knows the posters identity | no registration needed, so no private data will be stored about the user |
| DDOS attacks| No central server just StorJ |
| Sybill attacks | can be ignored, because all human beings who use this app have got a good heart. The others are still using dollars and euros.|
| AI might use the app on a simulator| People should meet in person to veryfiy the app, because the app is able to start scouping|
| User is able to install the app, spent 10 L on another app, deinstall and gets again 10L for installing.|Don't pay out 10 L on install. Only 1L|
| Check if we need to calculate a demurrage when paying | Should be ok, but we need a proof |
| StartScooping is public | Only registered accounts may scoop. While registration we could check some things.<br>We also check registration on setScooping every time.|
|Hackers might simulate the webservice in order to be able to scoop.|The webservice is encrypting the response and the client is aware that the response is not from the origianl webserver.|
|Hackers might fake the webservice and just replays the enrypted response.| We put a time based value into the encrypted response.|
|Someone might steal my computer with secret data| Build the lib on the server to hide this data.<br>Generate keys on the server and don't store them anywhere.|
|Users cannot scoop anymore when webservice is down.|In case someone removed the webserver. Only the scooping function is broken. The app can still be used to give gratitude.<br>Also no exceptions are thrown if disconnected from the internet.|
|Hackers might scan the APK for password and keys.|We switched to Go which creates native machine code. Also the keys are not stored in the APK. They are generated on runtime.<br>Thats the reason why our business logic is now close source.|
|Also replay attacks are a problem, when hackers are using our lib on a desktop or server in hundreds of instances.|The lib will only run on Android and IOS|
||Over all hacking is not a problem for the users.<br>If a hacker is able to crack the code he can only change the balance of his account.<br>Its like printing real good fake money or like painting Van Gogh's.<br>Even for users who receive those LMC its not a problem. When transferred, the LMC is becomming valid.|

