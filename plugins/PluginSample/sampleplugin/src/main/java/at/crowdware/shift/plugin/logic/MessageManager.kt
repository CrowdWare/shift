package at.crowdware.shift.plugin.logic

import java.util.Date
import java.util.jar.Attributes.Name

data class Message(val Name: String, val Message: String, val PeerUuid: String, val Time: String)

object MessageManager {

    fun getMessages(): List<Message>{
        var list: MutableList<Message> = mutableListOf()
        list.add(Message(Name = "Hans Meiser", Message = "Hallo wie geht es Dir? Mir geht es gut.", PeerUuid = "1234", "12:45"))
        list.add(Message(Name = "Bernd Schuster", Message = "We will meet next Sunday at 12:30 in the Park.", PeerUuid = "1235", "Mi"))
        list.add(Message(Name = "Frank", Message = "Alter, was hast Du denn damit gemeint?", PeerUuid = "1236", "Do"))
        return list
    }
}