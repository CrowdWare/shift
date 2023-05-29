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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.SyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.ByteArrayEntity
import cz.msebera.android.httpclient.message.BasicHeader
import nl.tudelft.ipv8.android.IPv8Android
import nl.tudelft.ipv8.android.keyvault.AndroidCryptoProvider
import nl.tudelft.ipv8.attestation.trustchain.TrustChainBlock
import nl.tudelft.ipv8.attestation.trustchain.TrustChainCommunity
import nl.tudelft.ipv8.keyvault.PrivateKey
import nl.tudelft.ipv8.keyvault.PublicKey
import nl.tudelft.ipv8.util.hexToBytes
import org.json.JSONObject
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.math.min
import java.time.temporal.ChronoUnit
import javax.crypto.spec.GCMParameterSpec
import kotlin.math.pow

class Backend {
    companion object {
        const val BLOCK_TYPE = "LMC"
        private const val serviceUrl = BuildConfig.WEB_SERVICE_URL
        private const val api_key = BuildConfig.API_KEY
        private const val secretKey = BuildConfig.SECRET_KEY
        private const val algorithm = BuildConfig.ALGORYTHM
        private const val user_agent = "Shift 1.0"
        private const val initial_amount = BuildConfig.INITIAL_AMOUNT
        private var account = Account()

        fun ByteArray.toHex() : String{
            val result = StringBuffer()

            forEach {
                val st = String.format("%02x", it)
                result.append(st)
            }
            return result.toString()
        }

        fun String.hexToByteArray(): ByteArray {
            val data = ByteArray(length / 2)
            for (i in data.indices) {
                data[i] = ((Character.digit(this[i * 2], 16) shl 4) + Character.digit(this[i * 2 + 1], 16)).toByte()
            }
            return data
        }


        fun encryptStringGCM(value: String): String {
            val keySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
            val cipher = Cipher.getInstance(algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            val result = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
            val iv = cipher.iv.copyOf()
            return iv.plus(result).toHex()
        }

        fun decryptStringGCM(value: String): String {
            val encryptedData = value.hexToByteArray()
            val iv = encryptedData.copyOfRange(0, 12) // GCM IV is usually 12 bytes
            val cipherText = encryptedData.copyOfRange(12, encryptedData.size)

            val keySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
            val cipher = Cipher.getInstance(algorithm)
            val gcmSpec = GCMParameterSpec(128, iv) // 128 bit auth tag length
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)

            val result = cipher.doFinal(cipherText)
            return String(result, Charsets.UTF_8)
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
            account.isScooping = true
            account.scooping = (System.currentTimeMillis() / 1000).toULong()
            Database.saveAccount(application.applicationContext)
            setScooping(application, true)
            addTransactionToTrustChain(initial_amount.toLong(), TransactionType.INITIAL_BOOKING)
        }

        fun setScooping(
            context: Context,
            async: Boolean = false
        ) {
            val client = if(async)
                AsyncHttpClient()
            else
                SyncHttpClient()
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
                    if(isError)
                        Log.e("setScooping", message)
                    else {
                        val count_1 = jsonResponse.getInt("count_1")
                        val count_2 = jsonResponse.getInt("count_2")
                        val count_3 = jsonResponse.getInt("count_3")
                        account.scooping = (System.currentTimeMillis() / 1000).toULong()
                        account.level_1_count = count_1.toUInt()
                        account.level_2_count = count_2.toUInt()
                        account.level_3_count = count_3.toUInt()
                        Database.saveAccount(context)
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    if(responseString != null)
                        Log.e("setScooping", responseString)
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
                if (block.isProposal && block.type == BLOCK_TYPE) {
                    val trans = parseTransaction(block)
                    if(trans.type == TransactionType.LMP) {
                        if (block.publicKey.contentEquals(IPv8Android.getInstance().myPeer.publicKey.keyToBin()))
                            trans.amount *= -1
                    }
                    list.add(0, trans)
                }
            }
            return list
        }

        fun getBalance(): Long {
            var balance: Long = 0

            if(!Network.isStarted)
                return 0

            val trustchain = IPv8Android.getInstance().getOverlay<TrustChainCommunity>()!!
            // collect amounts from blockchain
            for(block in trustchain.database.getAllBlocks()) {
                if (block.type == BLOCK_TYPE && block.isProposal) {
                    val trans = parseTransaction(block)
                    if(trans.type == TransactionType.SCOOPED || trans.type == TransactionType.INITIAL_BOOKING) {
                        balance += calculateWorth(trans.amount, trans.date)
                    } else if(trans.type == TransactionType.LMP) {
                        if (block.publicKey.contentEquals(IPv8Android.getInstance().myPeer.publicKey.keyToBin()))
                            balance -= calculateWorth(trans.amount, trans.date)
                        else
                            balance += calculateWorth(trans.amount, trans.date)
                    }
                }
            }
            // and also from daily transactions, no need to calculate worth
            for(t in account.transactions) {
                balance += t.amount
            }
            val minutes = ShiftChainService.minutesScooping()
            val amountOf20Minutes = minutes / 20f
            balance += (calcGrowPer20Minutes().toFloat() * amountOf20Minutes).toLong()
            return balance
        }

        fun addLiquid(context: Context, minutes: Int) {
            val growPer20Minutes = calcGrowPer20Minutes()
            val amountOf20Minutes = (minutes / 20)
            // if there is an old transaction we accumulate them and put them in the blockchain
            if (account.transactions.size > 0 && account.transactions.last().date < LocalDate.now()) {
                var balance = 0L
                for(t in account.transactions) {
                    balance += t.amount
                }
                // only book full liters
                val liter = (balance / 1000L).toLong()
                if(liter > 0)
                    addTransactionToTrustChain(liter, TransactionType.SCOOPED, bookingDate = LocalDate.now().minusDays(1))
                account.transactions.clear()
            }
            account.transactions.add(Transaction(growPer20Minutes.toLong() * amountOf20Minutes, "", date = LocalDate.now(), type=TransactionType.SCOOPED))
            account.scooping = 0u
            Database.saveAccount(context)
            setScooping(context)
        }
        fun getMaxGrow(): Long {
            return (165L + 10 * 25 + 100 * 5 + 1000 * 1) * 24 // 24 hours
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
            var balance = 0L
            println("Scooping for ${ShiftChainService.minutesScooping()} minutes")
            for(block in trustchain.database.getAllBlocks()) {
                if (block.type == BLOCK_TYPE) {
                    val blocktype = when {
                        block.isProposal -> "proposal "
                        block.isAgreement -> "agreement"
                        else -> "unknown  "
                    }
                    var amount = 0L
                    if(block.isProposal) {
                        val trans = parseTransaction(block)
                        amount = trans.amount
                        if(trans.type == TransactionType.SCOOPED || trans.type == TransactionType.INITIAL_BOOKING) {
                            balance += calculateWorth(trans.amount, trans.date)
                        }
                        else if(trans.type == TransactionType.LMP) {
                            if (block.publicKey.contentEquals(IPv8Android.getInstance().myPeer.publicKey.keyToBin())) {
                                balance -= calculateWorth(trans.amount, trans.date)
                            } else {
                                balance += calculateWorth(trans.amount, trans.date)
                            }
                        }
                    }
                    println("Block: ${amount*1000}  ${block.sequenceNumber}, insertTime: ${block.insertTime} ${block.publicKey.toHex()}, $blocktype, Gen: ${block.isGenesis} Self: ${block.isSelfSigned}")
                }
            }
            var i = 0
            for(t in account.transactions) {
                balance += t.amount
                i++
                println("Trans: ${t.amount} ${t.date} $i")
            }
            println("Balance: $balance")
        }

        private fun getGson(): Gson {
            val gson = GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, object : JsonSerializer<LocalDate> {
                    override fun serialize(
                        src: LocalDate,
                        typeOfSrc: Type,
                        context: JsonSerializationContext
                    ): JsonElement {
                        return JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    }
                })
                .registerTypeAdapter(LocalDate::class.java, object : JsonDeserializer<LocalDate> {
                    override fun deserialize(
                        json: JsonElement,
                        typeOfT: Type,
                        context: JsonDeserializationContext
                    ): LocalDate {
                        return LocalDate.parse(json.asString, DateTimeFormatter.ISO_LOCAL_DATE)
                    }
                })
                .create()
            return gson
        }

        fun parseTransaction(block: TrustChainBlock): Transaction {
            val gson = getGson()
            val data = block.transaction["data"] as String
            val json = decryptStringGCM(data)
            val trans: Transaction = gson.fromJson(json, Transaction::class.java)
            return Transaction(trans.amount, trans.from, trans.date, trans.purpose, trans.type)
        }

        /**
         * Adds transaction to the trustchain.
         *
         * @param amount The value in liter
         * @param type Transaction type like INITIAL_BOOKING, SCOOPING or LMP (liquid micro payment)
         * @param purpose Reason to send transaction
         * @param bookingDate When is the transaction created. When scooping the day before is used,
         * because the amount is cumulated on the day before.
         * @param publicKey The public key of the recipient
         */
        fun addTransactionToTrustChain(amount: Long,
                                       type: TransactionType,
                                       purpose: String = "",
                                       from: String = "",
                                       publicKey: ByteArray = IPv8Android.getInstance().myPeer.publicKey.keyToBin(),
                                       bookingDate:LocalDate = LocalDate.now()) {
            val trustchain = IPv8Android.getInstance().getOverlay<TrustChainCommunity>()!!
            val gson = getGson()
            val trans = Transaction(amount, from, bookingDate, purpose, type)
            val json = gson.toJson(trans)
            // fraud protection
            val enc = encryptStringGCM(json)
            // if publicKey is omitted block will be self signed (scooping)
            trustchain.createProposalBlock(BLOCK_TYPE, mapOf("data" to enc), publicKey)
        }

        /**
         * Calculates the worth of an amount with demurrage rate.
         *
         * @param amount The initial amount of coins liter.
         * @param transactionDate The date of the transaction or the date of scooping.
         * @return The current worth of the amount in milli liter.
         */
        private fun calculateWorth(amount: Long, transactionDate: LocalDate): Long {
            val currentDate = LocalDate.now()
            val daysPassed = ChronoUnit.DAYS.between(transactionDate, currentDate)
            val demurrageRate = 0.27 / 100
            var worth = (1000 * (1 - demurrageRate).pow(daysPassed.toInt())).toLong()
            worth *= amount
            return worth
        }
    }
}
