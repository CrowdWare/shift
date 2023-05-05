package at.crowdware.drawercompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import at.crowdware.shift.logic.Database

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isRegistered(applicationContext))

            startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this, JoinActivity::class.java))
        finish()
    }
}

fun isRegistered(applicationContext: Context): Boolean
{
    return Database.readAccount(applicationContext) != null
}