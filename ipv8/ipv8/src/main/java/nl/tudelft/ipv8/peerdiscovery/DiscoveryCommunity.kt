package nl.tudelft.ipv8.peerdiscovery

import mu.KotlinLogging
import nl.tudelft.ipv8.Community
import nl.tudelft.ipv8.Overlay
import nl.tudelft.ipv8.Peer
import nl.tudelft.ipv8.messaging.Address
import nl.tudelft.ipv8.messaging.Packet
import nl.tudelft.ipv8.messaging.payload.*
import nl.tudelft.ipv8.peerdiscovery.payload.PingPayload
import nl.tudelft.ipv8.peerdiscovery.payload.PongPayload
import nl.tudelft.ipv8.peerdiscovery.payload.SimilarityRequestPayload
import nl.tudelft.ipv8.peerdiscovery.payload.SimilarityResponsePayload
import java.util.*

private val logger = KotlinLogging.logger {}

class DiscoveryCommunity : Community(), PingOverlay {
    override val serviceId = "7e313685c1912a141279f8248fc8db5899c5df5a"

    private val pingRequestCache: MutableMap<Int, PingRequest> = mutableMapOf()

    init {
        messageHandlers[MessageId.SIMILARITY_REQUEST] = ::onSimilarityRequestPacket
        messageHandlers[MessageId.SIMILARITY_RESPONSE] = ::onSimilarityResponsePacket
        messageHandlers[MessageId.PING] = ::onPingPacket
        messageHandlers[MessageId.PONG] = ::onPongPacket
    }

    /*
     * Request creation
     */

    internal fun createSimilarityRequest(peer: Peer): ByteArray {
        val globalTime = claimGlobalTime()
        val payload = SimilarityRequestPayload(
            (globalTime % 65536u).toInt(),
            myEstimatedLan,
            myEstimatedWan,
            ConnectionType.UNKNOWN,
            getMyOverlays(peer)
        )
        logger.debug("-> $payload")
        return serializePacket(MessageId.SIMILARITY_REQUEST, payload, peer = peer)
    }

    fun sendSimilarityRequest(peer: Peer) {
        val myPeerSet = network.serviceOverlays.values.map { it.myPeer }.toSet()
        for (myPeer in myPeerSet) {
            val packet = createSimilarityRequest(myPeer)
            send(peer, packet)
        }
    }

    internal fun createSimilarityResponse(identifier: Int, peer: Peer): ByteArray {
        val payload = SimilarityResponsePayload(identifier, getMyOverlays(peer))
        logger.debug("-> $payload")
        return serializePacket(MessageId.SIMILARITY_RESPONSE, payload, peer = peer)
    }

    internal fun createPing(): Pair<Int, ByteArray> {
        val globalTime = claimGlobalTime()
        val payload = PingPayload((globalTime % UShort.MAX_VALUE).toInt())
        logger.debug("-> $payload")
        return Pair(payload.identifier, serializePacket(MessageId.PING, payload, sign = false))
    }

    override fun sendPing(peer: Peer) {
        val (identifier, packet) = createPing()

        val pingRequest = PingRequest(identifier, peer, Date())
        pingRequestCache[identifier] = pingRequest
        // TODO: implement cache timeout

        send(peer, packet)
    }

    internal fun createPong(identifier: Int): ByteArray {
        val payload = PongPayload(identifier)
        logger.debug("-> $payload")
        return serializePacket(MessageId.PONG, payload, sign = false)
    }

    /*
     * Request deserialization
     */

    internal fun onSimilarityRequestPacket(packet: Packet) {
        val (peer, payload) =
            packet.getAuthPayload(SimilarityRequestPayload.Deserializer)
        onSimilarityRequest(peer, payload)
    }

    internal fun onSimilarityResponsePacket(packet: Packet) {
        val (peer, payload) =
            packet.getAuthPayload(SimilarityResponsePayload.Deserializer)
        onSimilarityResponse(peer, payload)
    }

    internal fun onPingPacket(packet: Packet) {
        val payload = packet.getPayload(PingPayload.Deserializer)
        onPing(packet.source, payload)
    }

    internal fun onPongPacket(packet: Packet) {
        val payload = packet.getPayload(PongPayload.Deserializer)
        onPong(payload)
    }

    /*
     * Request handling
     */

    override fun onIntroductionResponse(
        peer: Peer,
        payload: IntroductionResponsePayload
    ) {
        super.onIntroductionResponse(peer, payload)
        sendSimilarityRequest(peer)
    }

    internal fun onSimilarityRequest(
        peer: Peer,
        payload: SimilarityRequestPayload
    ) {
        logger.debug("<- $payload")

        network.addVerifiedPeer(peer)
        network.discoverServices(peer, payload.preferenceList)

        val myPeerSet = network.serviceOverlays.values.map { it.myPeer }.toSet()
        for (myPeer in myPeerSet) {
            val packet = createSimilarityResponse(payload.identifier, myPeer)
            send(peer, packet)
        }
    }

    internal fun onSimilarityResponse(
        peer: Peer,
        payload: SimilarityResponsePayload
    ) {
        logger.debug("<- $payload")

        if (maxPeers >= 0 && getPeers().size >= maxPeers && !network.verifiedPeers.contains(peer)) {
            logger.info("Dropping similarity response from $peer, too many peers!")
            return
        }

        network.addVerifiedPeer(peer)
        network.discoverServices(peer, payload.preferenceList)
    }

    internal fun onPing(
        address: Address,
        payload: PingPayload
    ) {
        logger.debug("<- $payload")

        val packet = createPong(payload.identifier)
        send(address, packet)
    }

    internal fun onPong(
        payload: PongPayload
    ) {
        logger.debug("<- $payload")

        val pingRequest = pingRequestCache[payload.identifier]
        if (pingRequest != null) {
            pingRequest.peer.addPing((Date().time - pingRequest.startTime.time) / 1000.0)
            pingRequestCache.remove(payload.identifier)
        }
    }

    private fun getMyOverlays(peer: Peer): List<String> {
        return network.serviceOverlays
            .filter { it.value.myPeer == peer }
            .map { it.key }
    }

    object MessageId {
        const val SIMILARITY_REQUEST = 1
        const val SIMILARITY_RESPONSE = 2
        const val PING = 3
        const val PONG = 4
    }

    class PingRequest(val identifier: Int, val peer: Peer, val startTime: Date)

    class Factory : Overlay.Factory<DiscoveryCommunity>(DiscoveryCommunity::class.java)
}
