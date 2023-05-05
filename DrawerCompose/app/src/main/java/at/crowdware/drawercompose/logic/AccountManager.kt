package at.crowdware.shift.logic
/*
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountManager {
    private var _account: Account? = null
    private var _transactions: MutableList<Transaction> = mutableListOf()
    val Account? get(){
        if(_account == null)
            _account = Database.readAccount(context)
    }
    val Transactions get() = _transactions

    suspend fun loadTransactions(context: Context) = withContext(Dispatchers.IO) {
        // Load the account from the database
        _transactions = Database.readTransactions(context)
    }

    private fun loadAccount(context: Context)
    {
        _account = Database.readAccount(context)
    }
}*/