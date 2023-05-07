package at.crowdware.shift

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import at.crowdware.shift.ui.theme.DrawerComposeTheme
import at.crowdware.shift.logic.Database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrawerComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val hasJoined = remember { mutableStateOf(hasJoined(applicationContext)) }
                    if (hasJoined.value)
                        NavigationView()
                    else
                        JoinForm(hasJoined)
                }
            }
        }
    }
}

fun hasJoined(applicationContext: Context): Boolean
{
    return Database.readAccount(applicationContext) != null
}