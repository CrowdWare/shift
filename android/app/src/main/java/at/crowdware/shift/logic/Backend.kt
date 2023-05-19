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

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Base64
import android.util.Log
import at.crowdware.shift.BuildConfig
import at.crowdware.shift.R
import at.crowdware.shift.service.ShiftChainService
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.SyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.ByteArrayEntity
import cz.msebera.android.httpclient.message.BasicHeader
import nl.tudelft.ipv8.android.IPv8Android
import nl.tudelft.ipv8.android.keyvault.AndroidCryptoProvider
import nl.tudelft.ipv8.attestation.trustchain.EMPTY_PK
import nl.tudelft.ipv8.attestation.trustchain.TrustChainCommunity
import nl.tudelft.ipv8.keyvault.PrivateKey
import nl.tudelft.ipv8.util.hexToBytes
import org.json.JSONObject
import java.math.BigInteger
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.math.min

class Backend {
    companion object {
        private const val serviceUrl = BuildConfig.WEB_SERVICE_URL
        private const val api_key = BuildConfig.API_KEY
        private const val secretKey = BuildConfig.SECRET_KEY
        private const val algorithm = BuildConfig.ALGORYTHM
        private const val user_agent = "Shift 1.0"
        private const val MAX_TRANSACTIONS = 100
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

        fun startScooping(application: Application) {
            Network.startServive(application)
            account.scooping = (System.currentTimeMillis() / 1000).toULong()
            Database.saveAccount(application.applicationContext)
            setScooping(application, null, null)
            addTransactionToTrustChain(1000, TransactionType.INITIAL_BOOKING)
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

            account = Account(name.trim(), uuid.trim(), ruuid.trim(), country, language)
            val client = AsyncHttpClient()
            val url = serviceUrl + "register"
            val jsonParams = JSONObject()

            jsonParams.put("key", encryptStringGCM(api_key))
            jsonParams.put("name", account.name)
            jsonParams.put("uuid", account.uuid)
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
                        //addTransactionToTrustChain(1000L, TransactionType.INITIAL_BOOKING)
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
            if(account.privateKey.isEmpty()) {
                account.privateKey = AndroidCryptoProvider.generateKey().keyToBin().toHex()
            }
            return AndroidCryptoProvider.keyFromPrivateBin(account.privateKey.hexToBytes())
        }

        fun getTransactions(): MutableList<Transaction> {
            val list: MutableList<Transaction> = mutableListOf()
            if (!Network.isStarted)
                return list

            val trustchain = IPv8Android.getInstance().getOverlay<TrustChainCommunity>()!!
            for(block in trustchain.database.getAllBlocks()) {
                val amount: Long = block.transaction["amount"] as? Long ?: 0L
                val type: Int = (block.transaction["type"] as? BigInteger)?.toInt() ?: 0
                val date: Long = block.transaction["date"] as? Long ?: 0L
                val instant = Instant.ofEpochSecond(date)
                val d = instant.atZone(ZoneOffset.UTC).toLocalDate()
                val transactionType = enumValues<TransactionType>().getOrNull(type)
                if(block.isProposal && (transactionType == TransactionType.INITIAL_BOOKING
                            || transactionType == TransactionType.SCOOPED)) {
                    list.add(0, Transaction(amount = amount.toULong(), date = d, type = transactionType!!))
                }
            }
            return list
        }

        fun getBalance(): ULong {
            var amountOf20Minutes = 0.0f
            var balance: ULong = 0u

            if(!Network.isStarted)
                return 0u

            val trustchain = IPv8Android.getInstance().getOverlay<TrustChainCommunity>()!!
            // collect amounts from blockchain
            for(block in trustchain.database.getAllBlocks()) {
                val amount: Long = block.transaction["amount"] as? Long ?: 0L
                val type: Int = (block.transaction["type"] as? BigInteger)?.toInt() ?: 0
                if(block.isProposal && (type == TransactionType.INITIAL_BOOKING.value.toInt()
                            || type == TransactionType.SCOOPED.value.toInt())) {
                    balance += amount.toULong()
                }
            }
            // and also from daily transactions
            for(t in account.transactions) {
                balance += t.amount
            }
            val minutes = ShiftChainService.minutesScooping()
            amountOf20Minutes = minutes / 20f

            return balance + (calcGrowPer20Minutes().toFloat() * amountOf20Minutes).toULong()
        }

        fun addLiquid(context: Context, minutes: UInt) {
            val growPer20Minutes = calcGrowPer20Minutes()
            val amountOf20Minutes = (minutes / 20u)
            // if there is an old transaction we accumulate them and put them in the blockchain
            if (account.transactions.size > 0 && account.transactions.last().date != LocalDate.now()) {
                var balance = 0UL
                for(t in account.transactions) {
                    balance += t.amount
                }
                addTransactionToTrustChain(balance.toLong(), TransactionType.SCOOPED)
                account.transactions.clear()
            }
            account.transactions.add(Transaction(growPer20Minutes * amountOf20Minutes, date = LocalDate.now(), type=TransactionType.SCOOPED))
            Database.saveAccount(context)
            setScooping(context, null, null)
        }
        fun getMaxGrow(): Long {
            return 165L + 10 * 25 + 100 * 5 + 1000 * 1 * 24
        }

        private fun calcGrowPer20Minutes(): ULong {
            val growPer20Minutes = 165UL +
                    min(account.level_1_count, 10u) * 25u +
                    min(account.level_2_count, 100u) * 5u +
                    min(account.level_3_count, 1000u) * 1u
            return growPer20Minutes
        }

        fun dumpBlocks() {
            val trustchain = IPv8Android.getInstance().getOverlay<TrustChainCommunity>()!!
            var balance = 0UL
            for(block in trustchain.database.getAllBlocks()) {
                val type = when{
                    block.isProposal ->  "proposal "
                    block.isAgreement -> "agreement"
                    else ->              "unknown  "
                }
                val amount: Long = block.transaction["amount"] as? Long ?: 0L
                balance += amount.toULong()
                println("Block: ${block.sequenceNumber} ${block.publicKey.toHex()}, $type, ${block.transaction}, Gen: ${block.isGenesis} Self: ${block.isSelfSigned}")
            }
            for(t in account.transactions) {
                balance += t.amount
                println("Trans: ${t.amount} ${t.date}")
            }
            println("Balance: $balance")
        }

        fun addTransactionToTrustChain(amount: Long, type: TransactionType) {
            val trustchain = IPv8Android.getInstance().getOverlay<TrustChainCommunity>()!!
            val transaction = mapOf(
                "amount" to amount,
                "date" to (System.currentTimeMillis() / 1000),
                "type" to type.value.toInt())
            val publicKey = IPv8Android.getInstance().myPeer.publicKey.keyToBin()
            trustchain.createProposalBlock("LMC", transaction, publicKey)
        }
    }
}
