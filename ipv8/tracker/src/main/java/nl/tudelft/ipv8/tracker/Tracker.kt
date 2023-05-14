package nl.tudelft.ipv8.tracker

import mu.KotlinLogging
import nl.tudelft.ipv8.*
import nl.tudelft.ipv8.keyvault.defaultCryptoProvider
import nl.tudelft.ipv8.messaging.EndpointAggregator
import nl.tudelft.ipv8.messaging.Packet
import nl.tudelft.ipv8.messaging.payload.IntroductionRequestPayload
import nl.tudelft.ipv8.messaging.udp.UdpEndpoint
import nl.tudelft.ipv8.util.addressIsLan
import nl.tudelft.ipv8.util.sha1
import nl.tudelft.ipv8.util.toHex
import java.net.InetAddress
import java.util.*

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalUnsignedTypes::class)
class TrackerCommunity : Community() {
    override val serviceId: String = sha1(defaultCryptoProvider.generateKey().keyToBin()).toHex()

    override fun onPacket(packet: Packet) {
        val sourceAddress = packet.source
        val data = packet.data

        val probablePeer = network.getVerifiedByAddress(sourceAddress)
        if (probablePeer != null) {
            probablePeer.lastResponse = Date()
        }

        val msgId = data[prefix.size].toUByte().toInt()

        if (msgId == MessageId.INTRODUCTION_REQUEST) {
            val packetPrefix = data.copyOfRange(0, prefix.size)
            val (peer, payload) =
                packet.getAuthPayload(IntroductionRequestPayload.Deserializer)
            onGenericIntroductionRequest(peer, payload, packetPrefix)
        } else {
            logger.debug { "Tracker received unknown message $msgId" }
        }
    }

    private fun onGenericIntroductionRequest(
        peer: Peer,
        payload: IntroductionRequestPayload,
        prefix: ByteArray
    ) {
        logger.debug("<- $payload")

        // In case this is the first time the peer contacts a tracker, try to estimate their WAN
        // so we can already send a puncture request in this round
        val sourceWanAddress = if (payload.sourceWanAddress.isEmpty() &&
            !addressIsLan(peer.address)) peer.address else payload.sourceWanAddress

        val newPeer = peer.copy(
            lanAddress = payload.sourceLanAddress,
            wanAddress = sourceWanAddress
        )
        addVerifiedPeer(newPeer)

        val introPeers = network.getPeersForService(prefix.toHex())
            .filter { it != peer }
        val introPeer = if (introPeers.isNotEmpty()) introPeers.random() else null

        val packet = createIntroductionResponse(
            newPeer,
            payload.identifier,
            introduction = introPeer,
            prefix = prefix
        )

        send(peer.address, packet)
    }
}

class TrackerService {
    fun startTracker(port: Int) {
        val endpoint = EndpointAggregator(
            UdpEndpoint(port, InetAddress.getByName("0.0.0.0")),
            null
        )

        val config = IPv8Configuration(
            overlays = listOf(
                OverlayConfiguration(
                    Overlay.Factory(TrackerCommunity::class.java),
                    walkers = listOf(SimpleChurn.Factory())
                )
            )
        )

        val key = defaultCryptoProvider.generateKey()

        val myPeer = Peer(key)
        val ipv8 = IPv8(endpoint, config, myPeer)
        ipv8.start()

        logger.info { "Started tracker" }

        while (ipv8.isStarted()) {
            Thread.sleep(1000)
        }
    }
}

private const val PROPERTY_PORT = "port"
private const val DEFAULT_PORT = 8090

fun main() {
    val port = System.getProperty(PROPERTY_PORT, DEFAULT_PORT.toString()).toIntOrNull()
        ?: DEFAULT_PORT
    val service = TrackerService()
    service.startTracker(port)
}
