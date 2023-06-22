package at.crowdware.shift.logic

import org.json.JSONArray

data class Friend(val Name: String, val Scooping: Boolean, val Uuid: String, val Country: String, val FriendsCount: Int, val HasPeerDat: Boolean)

fun getFriendsFromJSON(jsonString: String): List<Friend> {
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
