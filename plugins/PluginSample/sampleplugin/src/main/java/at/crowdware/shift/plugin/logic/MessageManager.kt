package at.crowdware.shift.plugin.logic

import android.content.Context
import at.crowdware.shiftapi.getMessages
import at.crowdware.shiftapi.refreshMessages


data class Message(val Key: String, val Name: String, val Message: String, val PeerUuid: String, val Time: String, )

object MessageManager {
    var messages: MutableList<Message> = mutableListOf()

    fun initialize(context: Context) {

        // TODO: This should be done in a non blocking coroutine
        refreshMessages()
        val list = getMessages()
        messages.clear()
        for(msg in list) {
            messages.add(Message(Key = msg.Key, Name = msg.From, Message = msg.Message, PeerUuid = msg.PeerUuid, Time = msg.Time))
        }
    }

    fun getPeerMessages(): List<Message>{
        return messages
    }
}