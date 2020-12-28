# Startup the Net
We will start with one single device.
The first user is starting the app.
The app creates a new user record and stores it on the local database.
The user posts the first feed, which also will be stored into the database.

Then the second user starts the app and the above process is done on that device also.
Now these two people connect to each other via email, telegram, personally or whatever.
They exchange there UUID.

So device A is giving the UUID to the seconds device B.
From that moment on B knows A.
Then B give its UUID to A and A knows B now.

Then A is querying B for new feed.
B directly sends the first feed to A.

Then B is querying A for new feed.
A directly sends the first feed to B.

Then also they exchange the friendlist (just the UUIDs).

Then a third device C starts up and connects to A.
C know knows A and the friend B.
C is now querying A and B on a regular base.
So do A and B.
C and B are not connected directly because they do not friend up with each other, so that B does not get the friendlist from C and vice versa.


# Use cases
- Create record (first startup)
- post a feed locally (give it a UUID)
- connect to a new friend
- get friendlist (just the UUIDs)
- get feedlist from node (just the UUIDs)
- get feed from node (content for an UUID)

getFriendlist and getFeedlist are queryed on a regular base