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
import java.time.LocalDateTime
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class Backend {
    companion object {
        private const val serviceUrl = "http://shift.crowdware.at:8080/"
        private const val api_key = "1234567890123456"     // must be 16 bytes long // TODO...RAUS DAMIT
        private const val secretKey = "1234567890123456"   // must be 16 bytes long // TODO...RAUS DAMIT
        private var account = Account()
        private const val algorithm = "AES/CBC/PKCS5Padding"


        fun encryptString(plainText: String): String {
            val cipher = Cipher.getInstance(algorithm)
            val keySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
            val ivSpec = IvParameterSpec(byteArrayOf(
                0x01, 0x23, 0x45, 0x67,
                0x89.toByte(), 0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte(),
                0xFE.toByte(), 0xDC.toByte(), 0xBA.toByte(), 0x98.toByte(),
                0x76, 0x54, 0x32, 0x10
            ))
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val cipherText = cipher.doFinal(plainText.toByteArray(Charsets.ISO_8859_1))
            return Base64.encodeToString(cipherText, Base64.DEFAULT)
        }

        fun setAccount(acc: Account){account = acc }

        fun getAccount(): Account {return account}

        fun getFriendlist(
            context: Context,
            onFriendlistSucceed: (List<Friend>) -> Unit,
            onFriendlistFailed: (String?) -> Unit){
            val client = AsyncHttpClient()
            val url = serviceUrl + "matelist"
            val jsonParams = JSONObject()
            jsonParams.put("key", encryptString(api_key))
            jsonParams.put("uuid", account.uuid.trim())
            jsonParams.put("test", "false")

            val headers = arrayOf(
                BasicHeader("User-Agent", "Shift 1.0")
            )

            val entity = ByteArrayEntity(jsonParams.toString().toByteArray(Charsets.UTF_8))

            client.post(context, url, headers, entity, "application/json", object : TextHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    println(responseString)
                    val jsonResponse = JSONObject(responseString!!)
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
                                dataObj.getString("country"),
                                dataObj.getInt("friends_count")))
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
            jsonParams.put("key", encryptString(api_key))
            jsonParams.put("uuid", account.uuid)
            jsonParams.put("test", "false")

            val headers = arrayOf(
                BasicHeader("User-Agent", "Shift 1.0")
            )
            val entity = ByteArrayEntity(jsonParams.toString().toByteArray(Charsets.UTF_8))
            client.post(context, url, headers, entity, "application/json", object : TextHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    val jsonResponse = JSONObject(responseString!!)
                    val isError = jsonResponse.getBoolean("isError")
                    val message = jsonResponse.getString("message")
                    if(isError)
                        onScoopingFailed(message)
                    else {
                        val count_1 = jsonResponse.getInt("count_1")
                        val count_2 = jsonResponse.getInt("count_2")
                        val count_3 = jsonResponse.getInt("count_3")
                        account.scooping = (System.currentTimeMillis() / 1000).toULong()
                        account.level_1_count = count_1.toUInt()
                        account.level_2_count = count_2.toUInt()
                        account.level_3_count = count_3.toUInt()
                        Database.saveAccount(context)

                        println("level 1: ${account.level_1_count}, 2 ${account.level_2_count}, 3 ${account.level_3_count}")
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

            jsonParams.put("key", encryptString(api_key))
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
                    val jsonResponse = JSONObject(responseString!!)
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
                    val grow: UInt = 10000u + account.level_1_count * 1500u + account.level_2_count * 300u + account.level_3_count * 60u
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
            return balance + (hours * 500).toULong() +                          // 500 * 20 = 10000
                    (hours * account.level_1_count.toInt() * 75).toULong() +    //  75 * 20 = 1500
                    (hours * account.level_2_count.toInt() * 15).toULong() +    //  15 * 20 = 300
                    (hours * account.level_3_count.toInt() * 3).toULong()       //   3 * 20 = 60
        }
    }
}
