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
import java.io.File
import java.io.FileNotFoundException
import at.crowdware.shiftapi.ShiftPlugin


data class NavigationItem(val icon: ImageVector, val text: String, val id: String, val plugin: ShiftPlugin?, val index: Int)

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
                        NavigationItem(Icons.Default.Home, "Home", "home", null, 0) ,
                        NavigationItem(Icons.Default.Face, "Second", "second", null, 0)
                    )
                    loadPlugins(LocalContext.current, list)
                    NavigationView(list)
                }
            }
        }
    }
}

fun loadPlugins(
    context: Context,
    list: MutableList<NavigationItem>
) {
    val dir = File("${context.filesDir}/plugins/")
    if(dir.exists() && dir.isDirectory) {
        for(file in dir.listFiles()) {
            if(file.isFile && file.extension == "apk") {
                loadPlugin(context, file.name, list)
            }
        }
    }
}

fun loadPlugin(
    context: Context,
    filename: String,
    list: MutableList<NavigationItem>
){
    try {
        val file = "${context.filesDir}/plugins/$filename"
        val clu = ClassLoaderUtils()
        val pluginClass = clu.loadClass(file, context)
        val plugin = pluginClass!!.newInstance() as ShiftPlugin
        if (plugin is ShiftPlugin) {
            for(index in plugin.menuTexts().indices)
                list.add(NavigationItem(plugin.icons()[index], plugin.menuTexts()[index], filename + plugin.menuTexts()[index].lowercase(), plugin, index))
        }
    } catch(e:FileNotFoundException) {
        println("class not found ${e.message.toString()}")
        e.printStackTrace()
    }
}

