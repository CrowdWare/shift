    
Button(onClick = {
    val shift = IPv8Android.getInstance().getOverlay<ShiftCommunity>()!!
    shift.broadcastMessage("<title>", "<message>", "<url or empty>", "<admin code>")
}) {
    Text("<button text>")
}