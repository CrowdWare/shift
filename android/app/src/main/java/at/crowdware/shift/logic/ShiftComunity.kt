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

import android.util.Log
import nl.tudelft.ipv8.IPv4Address
import nl.tudelft.ipv8.Community
import nl.tudelft.ipv8.Peer
import nl.tudelft.ipv8.android.IPv8Android
import nl.tudelft.ipv8.attestation.trustchain.TrustChainCommunity
import nl.tudelft.ipv8.messaging.Deserializable
import nl.tudelft.ipv8.messaging.Packet
import nl.tudelft.ipv8.messaging.Serializable
import nl.tudelft.ipv8.messaging.payload.IntroductionResponsePayload
import java.nio.charset.Charset
import java.util.Date


class BroadcastMessage(val message: String) : Serializable {
    override fun serialize(): ByteArray {
        return message.toByteArray(charset = Charsets.UTF_8)
    }

    companion object Deserializer : Deserializable<BroadcastMessage> {
        override fun deserialize(buffer: ByteArray, offset: Int): Pair<BroadcastMessage, Int> {
            val messageString = String(buffer, offset, buffer.size - offset, Charsets.UTF_8)
            val broadcastMessage = BroadcastMessage(messageString)
            return Pair(broadcastMessage, buffer.size)
        }
    }
}

class ShiftCommunity : Community() {
    override val serviceId = "62824bd445a546ba803e7de9a8bb42d8cd92009c"
    private val MESSAGE_ID = 1

    val discoveredAddressesContacted: MutableMap<IPv4Address, Date> = mutableMapOf()
    val lastTrackerResponses = mutableMapOf<IPv4Address, Date>()

    init {
        messageHandlers[MESSAGE_ID] = ::onMessage
    }

    private fun onMessage(packet: Packet) {
        val (peer, payload) = packet.getAuthPayload(BroadcastMessage.Deserializer)
        Log.d("ShiftCommunity", peer.mid + ": " + payload.message)
    }

    fun broadcastGreeting() {
        for (peer in getPeers()) {
            val packet = serializePacket(MESSAGE_ID, BroadcastMessage("Hello!"))
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
