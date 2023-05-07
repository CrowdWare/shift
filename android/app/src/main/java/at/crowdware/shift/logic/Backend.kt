package at.crowdware.shift.logic

import android.content.Context
import android.util.Base64
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.ByteArrayEntity
import cz.msebera.android.httpclient.message.BasicHeader
import org.json.JSONObject
import java.util.UUID


class Backend {
    companion object {
        private const val serviceUrl = "http://128.140.48.116:8080/"
        private const val key = "1234567890" // TODO...RAUS DAMIT
        private var account = Account()

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

            account = Account(name, uuid, ruuid, country, language)
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
                        Database.saveAccount(context, account)
                        onJoinSucceed()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    onJoinFailed("There was a network error, try again later.")
                    println(responseString)
                }
            })
        }

        fun setAccount(acc: Account){account = acc }
        fun getAccount(): Account {return account}
        fun startScooping() {
            TODO("Not yet implemented")
        }
    }
}
