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
package at.crowdware.shift

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.theme.ShiftComposeTheme
import at.crowdware.shift.logic.Database
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.Network
import at.crowdware.shift.logic.PersistanceManager
import at.crowdware.shift.logic.PluginManager
import at.crowdware.shift.service.ShiftChainService
import at.crowdware.shift.ui.pages.Intro
import at.crowdware.shift.ui.pages.JoinForm
import at.crowdware.shift.ui.widgets.NavigationItem
import at.crowdware.shift.ui.widgets.NavigationView
import nl.tudelft.ipv8.android.keyvault.AndroidCryptoProvider
import nl.tudelft.ipv8.keyvault.defaultCryptoProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShiftComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    defaultCryptoProvider = AndroidCryptoProvider
                    LocaleManager.init(applicationContext, resources)

                    val hasJoined = remember { mutableStateOf(hasJoined(applicationContext)) }
                    val hasSeenDeleteWarning = remember { mutableStateOf(false) }

                    // first the account has to be created and user should have to trigger "Start Scooping" before starting the service
                    // that means the user should have agreed to start the service
                    if(Backend.getAccount().isScooping) {
                        Network.initIPv8(applicationContext as Application)
                        if(!ShiftChainService.isStarted()) {
                            val serviceIntent = Intent(this, ShiftChainService::class.java)
                            serviceIntent.putExtra("language", LocaleManager.getLanguage())
                            startService(serviceIntent)
                        }
                    }
                    if (hasJoined.value) {
                        // also code these in NavigationView.kt
                        val list = mutableListOf(
                            NavigationItem("home", Icons.Default.Home, stringResource(R.string.navigation_home)),
                            NavigationItem("friendlist",Icons.Default.Face, stringResource(R.string.navigation_friendlist)),
                            NavigationItem("settings", Icons.Default.Settings, stringResource(R.string.settings)),
                            NavigationItem("divider")
                        )
                        PluginManager.loadPlugins(LocalContext.current, list)
                        // navigation targets which are not listed in the drawer
                        list.add(NavigationItem(id="receive_gratitude_qrcode"))
                        list.add(NavigationItem(id="receive_gratitude"))
                        list.add(NavigationItem(id="give_gratitude"))
                        NavigationView(list)
                    }
                    else
                        if(hasSeenDeleteWarning.value || PersistanceManager.hasSeenDeleteWarning(this))
                            JoinForm(hasJoined, LocaleManager.getLanguage())
                        else
                            Intro(hasSeenDeleteWarning)
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.wrapContext(newBase!!))
    }

    fun hasJoined(applicationContext: Context): Boolean
    {
        return Database.readAccount(applicationContext) != null
    }
}

