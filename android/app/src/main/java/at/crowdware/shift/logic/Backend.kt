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
package at.crowdware.shift.logic

import android.content.Context
import android.content.res.Resources
import android.util.Base64
import android.util.Log
import at.crowdware.shift.BuildConfig
import at.crowdware.shift.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.SyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.ByteArrayEntity
import cz.msebera.android.httpclient.message.BasicHeader
import nl.tudelft.ipv8.android.keyvault.AndroidCryptoProvider
import nl.tudelft.ipv8.keyvault.PrivateKey
import nl.tudelft.ipv8.util.hexToBytes
import org.json.JSONObject
import java.time.LocalDate
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Backend {
    companion object {
        private const val serviceUrl = BuildConfig.WEB_SERVICE_URL
        private const val api_key = BuildConfig.API_KEY
        private const val secretKey = BuildConfig.SECRET_KEY
        private const val algorithm = BuildConfig.ALGORYTHM
        private const val user_agent = "Shift 1.0"
        private const val MAX_TRANSACTIONS = 100
        private const val SHIFT = "shift"
        private var account = Account()

        fun ByteArray.toHex() : String{
            val result = StringBuffer()

            forEach {
                val st = String.format("%02x", it)
                result.append(st)
            }
            return result.toString()
        }

        fun encryptStringGCM(value: String): String {
            val keySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
            val cipher = Cipher.getInstance(algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            val result = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
            val iv = cipher.iv.copyOf()
            return iv.plus(result).toHex()
        }
        fun setAccount(acc: Account) { account = acc }

        fun getAccount(): Account { return account }

        fun getFriendlist(
            context: Context,
            onFriendlistSucceed: (List<Friend>) -> Unit,
            onFriendlistFailed: (String?) -> Unit){
            val client = AsyncHttpClient()
            val url = serviceUrl + "matelist"
            val jsonParams = JSONObject()
            jsonParams.put("key", encryptStringGCM(api_key))
            jsonParams.put("uuid", account.uuid.trim())
            jsonParams.put("test", "false")

            val headers = arrayOf(
                BasicHeader("User-Agent", user_agent)
            )

            val entity = ByteArrayEntity(jsonParams.toString().toByteArray(Charsets.UTF_8))

            client.post(context, url, headers, entity, "application/json", object : TextHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
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
                    if(responseString != null)
                        Log.e("Shift", responseString)
                }
            })
        }

        fun startScooping(context: Context) {
            account.scooping = (System.currentTimeMillis() / 1000).toULong()
            Database.saveAccount(context)
        }

        fun setScooping(
            context: Context,
            onScoopingSucceed: (() -> Unit)?,
            onScoopingFailed: ((String?) -> Unit)?
        ) {
            val client = SyncHttpClient()
            val url = serviceUrl + "setscooping"
            val jsonParams = JSONObject()
            jsonParams.put("key", encryptStringGCM(api_key))
            jsonParams.put("uuid", account.uuid)
            jsonParams.put("test", "false")

            val headers = arrayOf(
                BasicHeader("User-Agent", user_agent)
            )
            val entity = ByteArrayEntity(jsonParams.toString().toByteArray(Charsets.UTF_8))
            client.post(context, url, headers, entity, "application/json", object : TextHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                    val jsonResponse = JSONObject(responseString!!)
                    val isError = jsonResponse.getBoolean("isError")
                    val message = jsonResponse.getString("message")
                    if(isError && onScoopingFailed != null)
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
                        if(onScoopingSucceed != null)
                            onScoopingSucceed()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    if (onScoopingFailed != null)
                        onScoopingFailed("There was a network error, try again later.")
                    if(responseString != null)
                        Log.e("Shift", responseString)
                }
            })
        }

        fun createAccount(
            context: Context,
            name: String,
            email: String,
            ruuid: String,
            country: String,
            language: String,
            onJoinSucceed: () -> Unit,
            onJoinFailed: (String?) -> Unit,
            res: Resources
        ) {
            val uuidBytes = UUID.randomUUID().toString().toByteArray()
            val uuid = Base64.encodeToString(uuidBytes, Base64.DEFAULT)
            if(name.isEmpty()) {
                onJoinFailed(res.getString(R.string.please_enter_your_name))
                return
            }
            else if(ruuid.isEmpty()) {
                onJoinFailed(res.getString(R.string.please_enter_the_invitation_code))
                return
            }
            else if(country.isEmpty()){
                onJoinFailed(res.getString(R.string.please_select_your_country))
                return
            }

            account = Account(name.trim(), uuid.trim(), email.trim(), ruuid.trim(), country, language)
            account.transactions.add(Transaction(amount=1000u, from=SHIFT, description = res.getString(
                            R.string.transaction_initial_liquid), date = LocalDate.now()))
            println("desc: ${account.transactions.first().description}")
            val client = AsyncHttpClient()
            val url = serviceUrl + "register"
            val jsonParams = JSONObject()

            jsonParams.put("key", encryptStringGCM(api_key))
            jsonParams.put("name", account.name)
            jsonParams.put("uuid", account.uuid)
            jsonParams.put("email", account.email)
            jsonParams.put("ruuid", account.ruuid)
            jsonParams.put("country", account.country)
            jsonParams.put("language", account.language)
            jsonParams.put("test","false")

            val headers = arrayOf(
                BasicHeader("User-Agent", user_agent)
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
                    if(responseString != null)
                        Log.e("Shift", responseString)
                }
            })
        }

        fun getPrivateKey(): PrivateKey {
            if(account.privateKey.isNullOrEmpty()) {
                account.privateKey = AndroidCryptoProvider.generateKey().keyToBin().toHex()
            }
            return AndroidCryptoProvider.keyFromPrivateBin(account.privateKey.hexToBytes())
        }

        fun getBalance(): ULong{
            var hours = 0.0f
            val time = (System.currentTimeMillis() / 1000).toULong()
            var balance: ULong = 0u
            for(t in account.transactions)
                balance += t.amount

            if(account.scooping > 0u){
                val seconds: ULong = time - account.scooping
                hours = (seconds.toFloat() / 60.0f / 60.0f)
            }
            return balance + (hours * 500).toULong() +                                                     // 500 * 20 = 10000
                    (hours * kotlin.math.min(account.level_1_count, 10u).toInt() * 75).toULong() +      //  75 * 20 = 1500
                    (hours * kotlin.math.min(account.level_2_count, 100u).toInt() * 15).toULong() +     //  15 * 20 = 300
                    (hours * kotlin.math.min(account.level_3_count, 1000u).toInt() * 3).toULong()       //   3 * 20 = 60
        }

        fun addLiquid(context: Context, hoursScooped: Float) {
            val growPerHour = 500UL +
                    kotlin.math.min(account.level_1_count, 10u) * 75u +
                    kotlin.math.min(account.level_2_count, 100u) * 15u +
                    kotlin.math.min(account.level_3_count, 1000u) * 3u
            // if we scooped on the same day, we just add to the amount
            if (account.transactions.first().date == LocalDate.now() && account.transactions.first().from == SHIFT) {
                account.transactions.first().amount += growPerHour * hoursScooped.toULong()
            } else {    // we create a new transaction
                if (account.transactions.size > MAX_TRANSACTIONS) {
                    // combine the last two bookings
                    val last = account.transactions[account.transactions.size - 1]
                    var prev = account.transactions[account.transactions.size - 2]
                    prev.amount = prev.amount + last.amount
                    prev.description = context.getString(R.string.transaction_subtotal)
                    prev.from = SHIFT
                    account.transactions.remove(account.transactions.last())
                }
                account.transactions.add(0, Transaction(growPerHour * hoursScooped.toULong(),
                    SHIFT, LocalDate.now(), context.getString(R.string.transaction_liquid_scooped)))
            }
            println("Liquid scooped: ${growPerHour * hoursScooped.toULong()}")

            account.scooping = 0u
            Database.saveAccount(context)
            setScooping(context, null, null)
        }
    }
}
