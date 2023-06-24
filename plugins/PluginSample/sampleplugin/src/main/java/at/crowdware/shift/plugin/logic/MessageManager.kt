package at.crowdware.shift.plugin.logic

import android.content.Context
import at.crowdware.shiftapi.ApiResponse
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import at.crowdware.shiftapi.FriendApi
import at.crowdware.shiftapi.getMessagesFromPeer
import at.crowdware.shiftapi.getPeerMessage
import java.io.Serializable

data class Message(val Key: String, val Name: String, val Message: String, val PeerUuid: String, val Time: String):
    Serializable

object MessageManager {
    var messages: MutableList<Message> = mutableListOf()
    lateinit var file: File

    fun initialize(context: Context) {
        file = File(context.filesDir, "messages.db")

        // TODO: This should be done in a coroutine
        loadMessages()
        val friends = FriendApi.getFriendList()
        for(friend in friends) {
            val response = getMessagesFromPeer(friend.Uuid)
            if(response is ApiResponse.Success) {
                val keys = response.data.split(",")
                for(key in keys) {
                    if(!messages.any { it.Key == key }) {
                        val response2 = getPeerMessage(friend.Uuid, key)
                        if (response2 is ApiResponse.Success) {
                            addMessage(
                                Message(
                                    Key = key,
                                    Name = friend.Name,
                                    Message = response2.data,
                                    PeerUuid = friend.Uuid,
                                    "ToDo"
                                )
                            )
                        }
                    }
                }
            }
        }
        saveMessages()
    }

    fun getPeerMessages(): List<Message>{
        return messages
    }

    private fun addMessage(message: Message) {
        messages.add(message)
        saveMessages()
    }

    private fun saveMessages() {
        val tempFile = File(file.parentFile, "messages.tmp")

        try {
            // Write to the temporary file
            val fileOutputStream = FileOutputStream(tempFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(messages)
            objectOutputStream.close()

            // Delete the original file if it exists
            if (file.exists()) {
                file.delete()
            }

            // Rename the temporary file to the original file
            tempFile.renameTo(file)

        } catch (e: IOException) {
            // Handle any IOException that occurs during file operations
            e.printStackTrace()

            // Delete the temporary file if it exists
            if (tempFile.exists()) {
                tempFile.delete()
            }
        }
    }

    private fun loadMessages() {
        if (file.exists()) {
            var objectInputStream: ObjectInputStream? = null

            try {
                val fileInputStream = FileInputStream(file)
                objectInputStream = ObjectInputStream(fileInputStream)
                messages = objectInputStream.readObject() as MutableList<Message>
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } finally {
                objectInputStream?.close()
            }
        }
    }
}