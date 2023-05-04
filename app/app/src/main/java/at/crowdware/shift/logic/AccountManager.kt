package at.crowdware.shift.logic

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountManager {
    private var account: Account? = null

    suspend fun loadAccount(context: Context) = withContext(Dispatchers.IO) {
        // Load the account from the database
        account = Database.readAccount(context)
    }

    fun getAccount(): Account? = account
}