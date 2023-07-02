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
package at.crowdware.shift.ui.widgets

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.crowdware.shift.MainActivity
import at.crowdware.shift.R
import at.crowdware.shift.ui.pages.AddNearbyFriend
import at.crowdware.shift.ui.pages.Friendlist
import at.crowdware.shift.ui.pages.GiveGratitude
import at.crowdware.shift.ui.pages.GiveGratitudeQRCode
import at.crowdware.shift.ui.pages.Home
import at.crowdware.shift.ui.pages.PluginSettings
import at.crowdware.shift.ui.pages.ReceiveGratitude
import at.crowdware.shift.ui.pages.ReceiveGratitudeQRCode
import at.crowdware.shift.ui.pages.ScanAgreement
import at.crowdware.shift.ui.pages.ScoopPage
import at.crowdware.shift.ui.pages.Settings
import at.crowdware.shiftapi.ui.theme.OnPrimary
import at.crowdware.shiftapi.ui.theme.Primary
import at.crowdware.shift.ui.viewmodels.GiveViewModel
import at.crowdware.shift.ui.viewmodels.ReceiveViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NavigationView(items: MutableList<NavigationItem>, mainActivity: MainActivity) {
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("home") }

    NavigationManager.setNavController(navController)
    val receiveViewModel = viewModel<ReceiveViewModel>()
    val giveViewModel = viewModel<GiveViewModel>()
    val title = remember { mutableStateOf("SHIFT") }
    var navTarget = remember { mutableStateOf("") }
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        for (index in items.indices) {
            composable(items[index].id) {
                when (items[index].id) {
                    "home" -> {
                        title.value = "SHIFT";navTarget.value = ""
                    }

                    "scooping" -> {
                        title.value = stringResource(R.string.scooping_menuitem);navTarget.value =
                            ""
                    }

                    "friendlist" -> {
                        title.value = stringResource(R.string.friendlist);navTarget.value = ""
                    }

                    "settings" -> {
                        title.value = stringResource(R.string.settings);navTarget.value = ""
                    }

                    "receive_gratitude" -> {
                        title.value = stringResource(R.string.receive_gratitude);navTarget.value =
                            "scooping"
                    }

                    "receive_gratitude_qrcode" -> {
                        title.value = stringResource(R.string.receive_gratitude);navTarget.value =
                            "scooping"
                    }

                    "give_gratitude" -> {
                        title.value = stringResource(R.string.give_gratitude);navTarget.value =
                            "scooping"
                    }

                    "give_gratitude_qrcode" -> {
                        title.value = stringResource(R.string.give_gratitude);navTarget.value =
                            "scooping"
                    }

                    "scan_agreement" -> {
                        title.value = stringResource(R.string.receive_gratitude);navTarget.value =
                            "scooping"
                    }

                    "plugin_settings" -> {
                        title.value = stringResource(R.string.plugin_settings);navTarget.value =
                            "settings"
                    }

                    "add_nearby_friend" -> {
                        title.value = stringResource(R.string.add_nearby_friend);navTarget.value =
                            "friendlist"
                    }

                    else -> {
                        title.value = items[index].text;navTarget.value = ""
                    }
                }
                NavigationDrawer(items, selectedItem, title.value, navTarget.value) {
                    when (items[index].id) {
                        // have a look at MainActivity for navigation
                        "home" -> Home()
                        "scooping" -> ScoopPage()
                        "friendlist" -> Friendlist()
                        "settings" -> Settings()
                        "receive_gratitude" -> ReceiveGratitude(receiveViewModel)
                        "receive_gratitude_qrcode" -> ReceiveGratitudeQRCode(receiveViewModel)
                        "give_gratitude" -> GiveGratitude(giveViewModel, mainActivity)
                        "give_gratitude_qrcode" -> GiveGratitudeQRCode(giveViewModel)
                        "scan_agreement" -> ScanAgreement(giveViewModel, mainActivity)
                        "plugin_settings" -> PluginSettings()
                        "add_nearby_friend" -> AddNearbyFriend(mainActivity)
                        else -> {
                            val plugin = items[index].plugin
                            runCatching {

                            plugin!!.pages()[items[index].index].invoke()
                            }.onFailure { exception ->
                                val errorMessage = "Plugin ${plugin!!.getName()} has been crashed!"
                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    items: List<NavigationItem>,
    selectedItem: MutableState<String>,
    title: String,
    navTarget: String = "",
    content: @Composable () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    if (openDialog.value) {
        About(
            openDialog = openDialog.value,
            onDismiss = { openDialog.value = false })
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerSheet(drawerState, items, selectedItem) },
        content = {
            Column() {
                CenterAlignedTopAppBar(
                    title = { Text(title) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Primary,
                        titleContentColor = OnPrimary,
                        navigationIconContentColor = OnPrimary,
                        actionIconContentColor = OnPrimary
                    ),
                    navigationIcon = {
                        if(navTarget != "") {
                            IconButton(onClick = { NavigationManager.navigate(navTarget)}) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        } else {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = null)
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { openDialog.value = true }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = stringResource(R.string.navigation_about)
                            )
                        }
                    }
                )
                content()
            }
        }
    )
}

@Composable
fun About(openDialog: Boolean, onDismiss: () -> Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(R.string.about_shift)) },
            text = {
                Text(
                    stringResource(R.string.about_dialog_text)
                )
            },
            confirmButton = { TextButton(onClick = onDismiss ) { Text("OK") } },
            dismissButton = {}
        )
    }
}