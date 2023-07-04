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
package at.crowdware.shiftapi

import lib.Lib
import lib.MessageTO
import org.json.JSONArray

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: String) : ApiResponse<Nothing>()
}

data class Message(
    val Key: String,
    val From: String,
    val PeerUuid: String,
    val Message: String,
    val Time: String,
    val Read: Boolean)

/**
 * Sends a message to a peer identified by the specified UUID.
 *
 * @param peerUuid The UUID of the peer to send the message to.
 * @param message The message to send.
 * @return An ApiResponse representing the result of the operation.
 *   - Success: An ApiResponse.Success instance containing the sent message.
 *   - Error: An ApiResponse.Error instance containing the corresponding error message.
 *
 * * Sample Usage:
 * ```
 * val peerUuid = "your_peer_uuid"
 * val message = "your_message"
 *
 * val response = sendPeerMessage(peerUuid, message)
 *
 * when (response) {
 *     is ApiResponse.Success -> {
 *         val ret = response.data
 *         println("Message sent successfully. Response: $ret")
 *     }
 *     is ApiResponse.Error -> {
 *         val error = response.error
 *         println("Failed to send message. Error: $error")
 *     }
 * }
 * ```
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
 * Deletes a peer message identified by the given peer UUID and key.
 *
 * @param peerUuid The UUID of the peer associated with the message.
 * @param key The key of the message to be deleted.
 * @return True is message could be deleted.
 *
 * Sample Usage:
 * ```
 * val response = deletePeerMessage("abc123", "message_key")
 * if (response)
 *     println("Message deleted successfully")
 * else
 *     println("Error deleting message")
 * ```
 */
fun deletePeerMessage(peerUuid: String, key: String): Boolean {
    return Lib.deletePeerMassage(peerUuid,key)
}

fun getMessages(): List<Message> {
    val jsonData = Lib.getMessages()
    return getMessageFromJSON(jsonData)
}

fun refreshMessages() {
    Lib.refreshMessages()
}

private fun getMessageFromJSON(jsonString: String): List<Message> {
    val jsonArray = JSONArray(jsonString)
    val messages = mutableListOf<Message>()

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val msg = Message(
            jsonObject.getString("Key"),
            jsonObject.getString("From"),
            jsonObject.getString("PeerUuid"),
            jsonObject.getString("Message"),
            jsonObject.getString("Time"),
            jsonObject.getBoolean("Read")
        )
        messages.add(msg)
    }
    return messages
}