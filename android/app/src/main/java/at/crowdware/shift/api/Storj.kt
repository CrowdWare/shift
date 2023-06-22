package at.crowdware.shift.api

import lib.Lib


sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: String) : ApiResponse<Nothing>()
}

/**
 * Sends a message to a peer identified by the specified UUID.
 *
 * @param peerUuid The UUID of the peer to send the message to.
 * @param message The message to send.
 * @return An ApiResponse representing the result of the operation.
 *   - Success: An ApiResponse.Success instance containing the sent message.
 *   - Error: An ApiResponse.Error instance containing the corresponding error message.
 *
 * @sample sendPeerMessageUsageSample
 */
fun sendPeerMessage(peerUuid: String, message: String): ApiResponse<String> {
    return when(val ret = Lib.sendMessageToPeer(peerUuid, message)) {
        "1" -> ApiResponse.Error("Peer not found.")
        "2" -> ApiResponse.Error("Unable to parse Storj access token.")
        "3" -> ApiResponse.Error("Error encrypting the message.")
        "4" -> ApiResponse.Error("Storj PUT failed.")
        else -> ApiResponse.Success(ret)
    }
}

/**
 * Retrieves message keys from a peer identified by the specified UUID.
 *
 * @param peerUuid The UUID of the peer to retrieve messages from.
 * @return An ApiResponse representing the result of the operation.
 *   - Success: An ApiResponse.Success instance containing the retrieved messages.
 *   - Error: An ApiResponse.Error instance containing the corresponding error message.
 *
 * @sample getMessagesFromPeerUsageSample
 */
fun getMessagesFromPeer(peerUuid: String): ApiResponse<String> {
    return when (val ret = Lib.getMessagesfromPeer(peerUuid)) {
        "1" -> ApiResponse.Error("Unable to parse Storj access token.")
        "2" -> ApiResponse.Error("List objects failed")
        else -> ApiResponse.Success(ret)
    }
}

/**
 * Retrieves a specific message from a peer identified by the specified UUID using the provided key.
 *
 * @param peerUuid The UUID of the peer to retrieve the message from.
 * @param key The key of the message to retrieve.
 * @return An ApiResponse representing the result of the operation.
 *   - Success: An ApiResponse.Success instance containing the retrieved message.
 *   - Error: An ApiResponse.Error instance containing the corresponding error message.
 *
 * @sample getPeerMessageUsageSample
 */
fun getPeerMessage(peerUuid: String, key: String): ApiResponse<String> {
    return when (val ret = Lib.getPeerMessage(peerUuid, key)) {
        "1" -> ApiResponse.Error("Parsing the access failed.")
        "2" -> ApiResponse.Error("Storj GET operation failed.")
        "3" -> ApiResponse.Error("Decryption failed.")
        else -> ApiResponse.Success(ret.substring(2))
    }
}

/**
 * Usage sample for the SendPeerMessage function.
 */
fun sendPeerMessageUsageSample() {
    val peerUuid = "your_peer_uuid"
    val message = "your_message"

    val response = sendPeerMessage(peerUuid, message)

    when (response) {
        is ApiResponse.Success -> {
            val ret = response.data
            println("Message sent successfully. Response: $ret")
        }
        is ApiResponse.Error -> {
            val error = response.error
            println("Failed to send message. Error: $error")
        }
    }
}

/**
 * Usage sample for the GetMessagesFromPeer function.
 */
fun getMessagesFromPeerUsageSample() {
    val peerUuid = "your_peer_uuid"

    val response = getMessagesFromPeer(peerUuid)

    when (response) {
        is ApiResponse.Success -> {
            val ret = response.data
            println("Messages retrieved successfully. Response: $ret")
        }
        is ApiResponse.Error -> {
            val error = response.error
            println("Failed to retrieve messages. Error: $error")
        }
    }
}

/**
 * Usage sample for the GetPeerMessage function.
 */
fun getPeerMessageUsageSample() {
    val peerUuid = "your_peer_uuid"
    val key = "your_message_key"

    val response = getPeerMessage(peerUuid, key)

    when (response) {
        is ApiResponse.Success -> {
            val ret = response.data
            println("Message retrieved successfully. Response: $ret")
        }
        is ApiResponse.Error -> {
            val error = response.error
            println("Failed to retrieve message. Error: $error")
        }
    }
}