package at.crowdware.shift

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import at.crowdware.shift.ui.theme.PluginSampleTheme
import java.io.FileNotFoundException


data class NavigationItem(val icon: ImageVector, val text: String, val id: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PluginSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val list = mutableListOf(
                        NavigationItem(Icons.Default.Home, "Home", "home") ,
                        NavigationItem(Icons.Default.Face, "Second", "second")
                    )
                    val internalPages = list.size
                    val plugin = loadPlugin(LocalContext.current, "sampleplugin-debug.apk", list)
                    NavigationView(list, plugin, internalPages)
                }
            }
        }
    }
}


fun loadPlugin(context: Context, filename: String, list: MutableList<NavigationItem>): ShiftPlugin?{
    try {
        val file = "${context.filesDir}/plugins/$filename"
        val clu = ClassLoaderUtils()
        val pluginClass = clu.loadClass(file, context)
        val plugin = pluginClass!!.newInstance() as ShiftPlugin
        if (plugin is ShiftPlugin) {
            for(index in plugin.menuTexts().indices)
                list.add(NavigationItem(plugin.icons()[index], plugin.menuTexts()[index], filename + plugin.menuTexts()[index].lowercase()))
        }
        return plugin
    } catch(e:FileNotFoundException) {
        println("class not found ${e.message.toString()}")
        e.printStackTrace()
        return null
    }
}

