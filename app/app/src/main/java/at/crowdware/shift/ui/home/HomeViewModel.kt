package at.crowdware.shift.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import at.crowdware.shift.logic.Account
import at.crowdware.shift.logic.AccountManager

class HomeViewModel: ViewModel() {
    private var _account: Account? = null
    private val account get() = _account!!
    fun setContext(context: Context)
    {
        _account = AccountManager.Account

        Toast.makeText(context, "Account loaded: ${account.name}", Toast.LENGTH_LONG).show()
    }
}