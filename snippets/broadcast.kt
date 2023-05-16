Button(
    onClick = {
        val community = IPv8Android.getInstance().getOverlay<ShiftCommunity>()!!
        community.broadcastGreeting()
    },
    modifier = Modifier.fillMaxWidth()
) {
    Text("Broadcast message", style = TextStyle(fontSize = 20.sp))
}


    
// ShiftCommunity
 
fun broadcastGreeting() {
    for (peer in getPeers()) {
        val packet = serializePacket(MESSAGE_ID_BROADCAST, BroadcastMessage("Updates available",
        "Hello, have a look at our website for news.", "http://shift.crowdware.at", "<admin code>"))
        send(peer.address, packet)
    }
}