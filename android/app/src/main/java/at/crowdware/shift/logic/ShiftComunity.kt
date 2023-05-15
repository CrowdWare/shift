/****************************************************************************
 * Copyright (C) 2023 CrowdWare
 *
 * This file is part of SHIFT.
 *
 *  SHIFT is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SHIFT is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************/
package at.crowdware.shift.logic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import at.crowdware.shift.R
import nl.tudelft.ipv8.IPv4Address
import nl.tudelft.ipv8.Community
import nl.tudelft.ipv8.Peer
import nl.tudelft.ipv8.android.IPv8Android
import nl.tudelft.ipv8.attestation.trustchain.TrustChainCommunity
import nl.tudelft.ipv8.messaging.Deserializable
import nl.tudelft.ipv8.messaging.Packet
import nl.tudelft.ipv8.messaging.Serializable
import nl.tudelft.ipv8.messaging.payload.IntroductionResponsePayload
import java.util.Date

fun sendNotification(context: Context, title: String, message: String, url: String = "") {
    val NOTIFICATION_ID = 123
    val channelId = "your_channel_id"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, context.getString(R.string.broadcasts_channel), importance)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.icon)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    if(url.isNotEmpty()) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val action = NotificationCompat.Action(
            R.drawable.icon,
            context.getString(R.string.open_link),
            pendingIntent
        )
        builder.setContentIntent(pendingIntent)
        builder.addAction(action)
    }
    with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
    }
}
class BroadcastMessage(val message: String, val url: String = "") : Serializable {
    override fun serialize(): ByteArray {
        val serializedData = "$message|$url" // Combine message and url with a delimiter
        return serializedData.toByteArray(Charsets.UTF_8)
    }

    companion object Deserializer : Deserializable<BroadcastMessage> {
        override fun deserialize(buffer: ByteArray, offset: Int): Pair<BroadcastMessage, Int> {
            val serializedData = String(buffer, offset, buffer.size - offset, Charsets.UTF_8)
            val parts = serializedData.split("|")
            val message = parts.getOrNull(0) ?: ""
            val url = parts.getOrNull(1) ?: ""
            val broadcastMessage = BroadcastMessage(message, url)
            return Pair(broadcastMessage, buffer.size)
        }
    }
}

class ShiftCommunity : Community() {
    override val serviceId = "62824bd445a546ba803e7de9a8bb42d8cd92009c"
    private val MESSAGE_ID = 1
    var context: Context? = null

    val discoveredAddressesContacted: MutableMap<IPv4Address, Date> = mutableMapOf()
    val lastTrackerResponses = mutableMapOf<IPv4Address, Date>()

    init {
        messageHandlers[MESSAGE_ID] = ::onMessage
    }

    private fun onMessage(packet: Packet) {
        val (peer, payload) = packet.getAuthPayload(BroadcastMessage.Deserializer)
        Log.d("ShiftCommunity", peer.mid + ": " + payload.message)

        sendNotification(context!!, "Message", payload.message, payload.url)
    }

    fun broadcastGreeting() {
        for (peer in getPeers()) {
            val packet = serializePacket(MESSAGE_ID, BroadcastMessage("Hello, have a look at our website for news.", "http://shift.crowdware.at"))
            send(peer.address, packet)
        }
    }

    override fun walkTo(address: IPv4Address) {
        super.walkTo(address)

        discoveredAddressesContacted[address] = Date()
    }

    // Retrieve the trustchain community
    private fun getTrustChainCommunity(): TrustChainCommunity {
        return IPv8Android.getInstance().getOverlay()
            ?: throw IllegalStateException("TrustChainCommunity is not configured")
    }

    override fun onIntroductionResponse(peer: Peer, payload: IntroductionResponsePayload) {
        super.onIntroductionResponse(peer, payload)

        if (peer.address in DEFAULT_ADDRESSES) {
            lastTrackerResponses[peer.address] = Date()
        }
    }
}
