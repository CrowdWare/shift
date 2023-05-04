package at.crowdware.shift

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import at.crowdware.shift.logic.AccountManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class SplashActivity : AppCompatActivity() {
    private val SPLASH_SCREEN_TIMEOUT = 2000L // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val userJoined = isUserRegistered()
        if(userJoined) {
            // load the account while waiting for the splash screen
            lifecycleScope.launch(Dispatchers.Main) {
                AccountManager.loadAccount(applicationContext)
            }
        }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (userJoined) {
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
        val fileExtern = File(getExternalFilesDir(null), "shift.db")
        if(fileExtern.exists())
            return true
        return File(applicationContext.filesDir, "shift.db").exists()
    }
}
