Button(
            onClick = {
                val community = IPv8Android.getInstance().getOverlay<ShiftCommunity>()!!
                community.broadcastGreeting()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Broadcast message", style = TextStyle(fontSize = 20.sp))
        }