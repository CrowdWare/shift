package at.crowdware.shift.logic

import com.loopj.android.http.AsyncHttpClient.log
import org.json.JSONArray

data class Transaction (val amount: Long, val purpose: String, val date: Long, val typ: Long)

fun getTransactionsFromJSON(jsonString: String): List<Transaction> {
    val jsonArray = JSONArray(jsonString)
    val transactions = mutableListOf<Transaction>()

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val transaction = Transaction(
            jsonObject.getLong("Amount"),
            jsonObject.getString("Purpose"),
            jsonObject.getLong("Date"),
            jsonObject.getLong("Typ")
        )
        transactions.add(transaction)
    }
    return transactions
}