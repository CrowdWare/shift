package at.crowdware.shift


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import at.crowdware.shift.logic.AccountManager
import at.crowdware.shift.logic.Backend


class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()

        val joinButton = findViewById<Button>(R.id.button_join)

        joinButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val friend = findViewById<EditText>(R.id.editTextFriend).text.toString()
            val country = findViewById<Spinner>(R.id.spinnerCountry).selectedItem.toString()
            val language = findViewById<Spinner>(R.id.spinnerLanguage).selectedItem.toString()

            if(name.isEmpty())
                showError("Name should be entered")
            else if(friend.isEmpty())
                showError("Friend should be entered")
            else if(country.isEmpty())
                showError("Country should be selected")
            else if(language.isEmpty())
                showError("Language should be selected")
            else {
                joinButton.isEnabled = false
                Backend.createAccount(this, name, friend, country, language)
            }
        }
    }
    fun Joined()
    {
        AccountManager.loadAccount(this)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun JoinFailed(responseString: String?)
    {
        showError("There was an error joining the server!")
        println(responseString)
    }

    private fun showError(msg: String){
        val tv = findViewById<TextView>(R.id.text_error)
        tv.visibility = View.VISIBLE
        tv.text = msg
    }
}
