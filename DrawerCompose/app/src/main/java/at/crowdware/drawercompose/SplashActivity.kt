package at.crowdware.drawercompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity



class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
    }
}