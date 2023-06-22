/****************************************************************************
 * Copyright (C) 2023 CrowdWare
 *
 * This file is part of SHIFT.
 *
 *  SHIFT is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SHIFT is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************/
package at.crowdware.shift.logic

import android.content.Context
import at.crowdware.shiftapi.ShiftPlugin
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
            println("file not found ${e.message.toString()}")
            e.printStackTrace()
        } catch(e: ClassNotFoundException) {
            println("class not found ${e.message.toString()}")
            e.printStackTrace()
        }
    }
}