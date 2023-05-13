package nl.tudelft.ipv8

import nl.tudelft.ipv8.messaging.*
import java.net.InetSocketAddress
import java.net.SocketAddress

/**
 * The pair of an IP address and a port.
 */
data class IPv4Address(
    val ip: String,
    val port: Int
) : Serializable, Address {
    override fun serialize(): ByteArray {
        val parts = ip.split(".")
        val ipBytes = ByteArray(4)
        for (i in ipBytes.indices) {
            ipBytes[i] = parts[i].toUByte().toByte()
        }
        return ipBytes + serializeUShort(port)
    }

    fun toSocketAddress(): SocketAddress {
        return InetSocketAddress(ip, port)
    }

    fun isEmpty(): Boolean {
        return this == EMPTY
    }

    fun isLoopback(): Boolean {
        return ip == "127.0.0.1"
    }

    override fun toString(): String {
        return "$ip:$port"
    }

    companion object : Deserializable<IPv4Address> {
        override fun deserialize(buffer: ByteArray, offset: Int): Pair<IPv4Address, Int> {
            var localOffset = 0

            val ip = "" +
                    buffer[offset + 0].toUByte() + "." +
                    buffer[offset + 1].toUByte() + "." +
                    buffer[offset + 2].toUByte() + "." +
                    buffer[offset + 3].toUByte()
            localOffset += 4
            val port = deserializeUShort(buffer, offset + localOffset)
            localOffset += SERIALIZED_USHORT_SIZE
            return Pair(IPv4Address(ip, port), localOffset)
        }

        const val SERIALIZED_SIZE = 6

        val EMPTY = IPv4Address("0.0.0.0", 0)
    }
}
