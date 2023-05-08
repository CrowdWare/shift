package at.crowdware.shift.logic

import android.content.Context
import android.os.Build
import android.util.Base64
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.ByteArrayEntity
import cz.msebera.android.httpclient.message.BasicHeader
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


class Backend {
    companion object {
        private const val serviceUrl = "http://shift.crowdware.at:8080/"
        private const val key = "1234567890" // TODO...RAUS DAMIT
        private var account = Account()

        fun getFriendlist(
            context: Context,
            onFriendlistSucceed: (List<Friend>) -> Unit,
            onFriendlistFailed: (String?) -> Unit){
            val client = AsyncHttpClient()
            val url = serviceUrl + "matelist"
            val jsonParams = JSONObject()
            jsonParams.put("key", key)
            jsonParams.put("uuid", account.uuid.trim())
            jsonParams.put("test", "false")

            val headers = arrayOf(
                BasicHeader("User-Agent", "Shift 1.0")
            )
            val entity = ByteArrayEntity(jsonParams.toString().toByteArray(Charsets.UTF_8))
            client.post(context, url, headers, entity, "application/json", object : TextHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    println(responseString)
                    val jsonResponse = JSONObject(responseString)
                    val isError = jsonResponse.getBoolean("isError")
                    val message = jsonResponse.getString("message")
                    if(isError)
                        onFriendlistFailed(message)
                    else {
                        val data = jsonResponse.getJSONArray("data")
                        val dataList = mutableListOf<Friend>()
                        for (i in 0 until data.length()) {
                            val dataObj = data.getJSONObject(i)
                            dataList.add(Friend(dataObj.getString("name"),
                                dataObj.getBoolean("scooping"),
                                dataObj.getString("uuid"),
                                dataObj.getString("country")))
                        }
                        onFriendlistSucceed(dataList)
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    onFriendlistFailed("There was a network error, try again later.")
                    println(responseString)
                }
            })
        }
        fun setScooping(
            context: Context,
            onScoopingSucceed: () -> Unit,
            onScoopingFailed: (String?) -> Unit) {
            val client = AsyncHttpClient()
            val url = serviceUrl + "setscooping"
            val jsonParams = JSONObject()
            jsonParams.put("key", key)
            jsonParams.put("uuid", account.uuid)
            jsonParams.put("test", "false")

            val headers = arrayOf(
                BasicHeader("User-Agent", "Shift 1.0")
            )
            val entity = ByteArrayEntity(jsonParams.toString().toByteArray(Charsets.UTF_8))
            client.post(context, url, headers, entity, "application/json", object : TextHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    val jsonResponse = JSONObject(responseString)
                    val isError = jsonResponse.getBoolean("isError")
                    val message = jsonResponse.getString("message")
                    if(isError)
                        onScoopingFailed(message)
                    else {
                        val mates = jsonResponse.getInt("mates")
                        account.scooping = (System.currentTimeMillis() / 1000).toULong()
                        account.matescount = mates.toUInt()
                        Database.saveAccount(context)
                        onScoopingSucceed()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    onScoopingFailed("There was a network error, try again later.")
                    println(responseString)
                }
            })
        }

        fun createAccount(
            context: Context,
            name: String,
            ruuid: String,
            country: String,
            language: String,
            onJoinSucceed: () -> Unit,
            onJoinFailed: (String?) -> Unit
        ) {
            val uuidBytes = UUID.randomUUID().toString().toByteArray()
            val uuid = Base64.encodeToString(uuidBytes, Base64.DEFAULT)
            if(name.isEmpty()) {
                onJoinFailed("Please enter your name!")
                return
            }
            else if(ruuid.isEmpty()) {
                onJoinFailed("Please enter the invitation code.")
                return
            }
            else if(country.isEmpty()){
                onJoinFailed("Please select your country.")
                return
            }
            else if(language.isEmpty()){
                onJoinFailed("Please select a language.")
                return
            }

            account = Account(name.trim(), uuid.trim(), ruuid.trim(), country, language)
            account.transactions.add(Transaction(amount=1000u, from="App", description = "Initial liquid", date = LocalDateTime.now()))

            val client = AsyncHttpClient()
            val url = serviceUrl + "register"
            val jsonParams = JSONObject()

            jsonParams.put("key", key)
            jsonParams.put("name", account.name)
            jsonParams.put("uuid", account.uuid)
            jsonParams.put("ruuid", account.ruuid)
            jsonParams.put("country", account.country)
            jsonParams.put("language", account.language)
            jsonParams.put("test","false")

            val headers = arrayOf(
                BasicHeader("User-Agent", "Shift 1.0")
            )
            val entity = ByteArrayEntity(jsonParams.toString().toByteArray(Charsets.UTF_8))

            client.post(context, url, headers, entity, "application/json", object : TextHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    val jsonResponse = JSONObject(responseString)
                    val isError = jsonResponse.getBoolean("isError")
                    val message = jsonResponse.getString("message")
                     if(isError)
                        onJoinFailed(message)
                    else {
                        Database.saveAccount(context)
                        onJoinSucceed()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    onJoinFailed("There was a network error, try again later.")
                    println(responseString)
                }
            })
        }

        fun getBalance(context: Context): ULong{
            var hours = 0.0f
            val time = (System.currentTimeMillis() / 1000).toULong()
            var balance: ULong = 0u
            for(t in account.transactions)
                balance += t.amount

            if(account.scooping > 0u){
                val seconds: ULong = time - account.scooping
                hours = (seconds.toFloat() / 60.0f / 60.0f)
                if(hours > 20.0) {  //scooping stops after 20 hours
                    hours = 0f
                    val grow: UInt = 10000u + account.matescount * 1500u
                    balance += grow // 10 + 1.5 (per mate) LMC per day added
                    if (account.transactions.size > 29)
                    {
                        // combine the last two bookings
                        val last = account.transactions[account.transactions.size - 1]
                        var prev = account.transactions[account.transactions.size - 2]
                        prev.amount = prev.amount + last.amount
                        prev.description = "Subtotal"
                        account.transactions.remove(account.transactions.last())
                    }
                    account.transactions.add(0, Transaction(grow.toULong(),"App", LocalDateTime.now(),"Liquid scooped"))
                    account.scooping = 0u
                    Database.saveAccount(context)
                }
            }
            // TODO use all levels of mates
            return balance + (hours * 500).toULong() + (hours * account.matescount.toInt() * 75).toULong()
        }

        fun IsRunningOnEmulator(): Boolean{
            return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86")
        }
        fun setAccount(acc: Account){account = acc }
        fun getAccount(): Account {return account}
    }
}
