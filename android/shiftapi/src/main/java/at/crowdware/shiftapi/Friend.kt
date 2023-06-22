package at.crowdware.shiftapi

import lib.Lib
import org.json.JSONArray


/**
Represents a friend entity.
@property Name The name of the friend.
@property Scooping Indicates if the friend is currently scooping.
@property Uuid The UUID of the friend.
@property Country The country of the friend.
@property FriendsCount The number of friends the friend has.
@property HasPeerDat Indicates if the public key and Storj access data has been scanned.
 */
data class Friend(val Name: String, val Scooping: Boolean, val Uuid: String, val Country: String, val FriendsCount: Int, val HasPeerDat: Boolean)

object FriendApi {
    /**
    Retrieves the list of friends from the library and converts it to a list of Friend objects.
    @return The list of friends.
     */
    fun getFriendList(): List<Friend>{
        val json = Lib.getMatelist()
        return getFriendsFromJSON(json)
    }

    private fun getFriendsFromJSON(jsonString: String): List<Friend> {
        val jsonArray = JSONArray(jsonString)
        val friends = mutableListOf<Friend>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val friend = Friend(
                jsonObject.getString("Name"),
                jsonObject.getBoolean("Scooping"),
                jsonObject.getString("Uuid"),
                jsonObject.getString("Country"),
                jsonObject.getInt("FriendsCount"),
                jsonObject.getBoolean("HasPeerData")
            )
            friends.add(friend)
        }
        return friends
    }
}


