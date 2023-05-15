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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import at.crowdware.shift.ui.theme.DrawerComposeTheme
import at.crowdware.shift.logic.Database
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.Network
import at.crowdware.shift.logic.PersistanceManager
import at.crowdware.shift.service.ShiftChainService
import nl.tudelft.ipv8.android.keyvault.AndroidCryptoProvider
import nl.tudelft.ipv8.keyvault.defaultCryptoProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrawerComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    defaultCryptoProvider = AndroidCryptoProvider
                    LocaleManager.init(applicationContext)

                    val serviceIntent = Intent(this, ShiftChainService::class.java)
                    serviceIntent.putExtra("language", LocaleManager.getLanguage())
                    startService(serviceIntent)

                    Network.initIPv8(applicationContext as Application)
                    val hasJoined = remember { mutableStateOf(hasJoined(applicationContext)) }
                    val hasSeenDeleteWarning = remember { mutableStateOf(false) }
                    if (hasJoined.value)
                        NavigationView()
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

