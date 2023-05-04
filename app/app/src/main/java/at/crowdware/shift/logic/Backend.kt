package at.crowdware.shift.logic
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.content.Context
import android.os.Environment
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import at.crowdware.shift.R
import at.crowdware.shift.RegistrationActivity
import com.loopj.android.http.AsyncHttpClient
import cz.msebera.android.httpclient.Header
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.entity.ByteArrayEntity
import cz.msebera.android.httpclient.message.BasicHeader
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.Date
import java.util.UUID

data class Transaction(val amount: Long, val from: String, val date: Date, val description: String): Serializable

data class Account(val name: String,
                   val uuid: String,
                   val ruuid: String,
                   val country: String,
                   val language: String,
                   var balance: Long = 0,
                   var scooping: ULong = 0u,
                   val transactions: MutableList<Transaction> = mutableListOf()
) : Serializable

class Backend {
    companion object {
        private const val serviceUrl = "http://128.140.48.116:8080/"
        private const val key = "1234567890" // TODO...RAUS DAMIT

        fun createAccount(context: RegistrationActivity, name: String, ruuid: String, country: String, language: String) {
            val uuidBytes = UUID.randomUUID().toString().toByteArray()
            val uuid = Base64.encodeToString(uuidBytes, Base64.DEFAULT)
            var ru: String
            if (ruuid == "me")
                ru = uuid
            else
                ru = ruuid
            val account = Account(name, uuid, ru, country, language)
            registerAccount(context, account);
        }

        private fun registerAccount(context: RegistrationActivity, account: Account) {
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
                    Database.saveAccount(context, account)
                    context.Joined()
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    context.JoinFailed(responseString)
                }
            })
        }
    }
}
