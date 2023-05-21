package at.crowdware.shift.logic

import android.content.Context
import at.crowdware.shift.ShiftPlugin
import at.crowdware.shift.ui.widgets.NavigationItem
import java.io.File
import java.io.FileNotFoundException

data class Plugin(val filename: String, val displayName: String, val version: String)

object PluginManager {
    val list: MutableList<Plugin> = mutableListOf()

    fun getPluginList(): List<Plugin>  {
        return list.toList()
    }

    fun loadPlugins(
        context: Context,
        list: MutableList<NavigationItem>
    ) {
        PluginManager.list.clear()
        val dir = File("${context.filesDir}/plugins/")
        if(dir.exists() && dir.isDirectory) {
            for(file in dir.listFiles()!!) {
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
            PluginManager.list.add(Plugin(filename, plugin.getName(), plugin.getVersion()))
            for(index in plugin.menuTexts().indices) {
                list.add(
                    NavigationItem(
                        filename + plugin.menuTexts()[index].lowercase(),
                        plugin.icons()[index],
                        plugin.menuTexts()[index],
                        plugin,
                        index
                    )
                )
            }
        } catch(e: FileNotFoundException) {
            println("class not found ${e.message.toString()}")
            e.printStackTrace()
        }
    }
}