package at.crowdware.shift

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
                    //checkPermissions()
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

/*
@Composable
fun checkPermissions(): Boolean {

    var permissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    DisposableEffect(Unit) {
        val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
        permissionGranted = permissionStatus == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(permission),
                1
            )
        }
        onDispose { }
    }
    return permissionGranted
}
*/