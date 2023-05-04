package at.crowdware.shift


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import at.crowdware.shift.logic.AccountManager
import at.crowdware.shift.logic.Backend


class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()

        val myButton = findViewById<Button>(R.id.button_join)
        myButton.setOnClickListener {
            Backend.createAccount(this, "Art", "me", "Germany", "Deutsch")
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
        val tv = findViewById<TextView>(R.id.text_error)
        tv.visibility = View.VISIBLE
        tv.text = "There was an error joining the server!"
        println(responseString)
    }
}
