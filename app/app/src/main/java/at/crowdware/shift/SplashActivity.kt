package at.crowdware.shift

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    private val SPLASH_SCREEN_TIMEOUT = 2000L // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (isUserRegistered()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, SPLASH_SCREEN_TIMEOUT)
    }

    private fun isUserRegistered(): Boolean {
        // Query the database or shared preferences or any other storage mechanism
        // to check whether the user is registered or not
        // Return true if the user is registered, false otherwise
        return true
    }
}
