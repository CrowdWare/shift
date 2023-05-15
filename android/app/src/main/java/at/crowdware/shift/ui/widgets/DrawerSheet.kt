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

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.crowdware.shift.R
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.ui.theme.DrawerComposeTheme
import kotlinx.coroutines.launch

data class MenuItem(val icon: ImageVector, val text: String, val id: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerSheet(drawerState: DrawerState, navController: NavController, selectedItem: MutableState<String>){
    val items = listOf(
        MenuItem(Icons.Default.Home, stringResource(R.string.navigation_home), "home"),
        MenuItem(Icons.Default.Face, stringResource(R.string.navigation_friendlist), "friendlist"))

    val scope = rememberCoroutineScope()

    //region vars for the DropDownlistbox
    val context = LocalContext.current
    val language_codes = listOf("en", "de", "es", "fr", "pt", "eo")
    val languages = listOf(
        stringResource(R.string.language_english),
        stringResource(R.string.language_german),
        stringResource(R.string.language_spanish),
        stringResource(R.string.language_french),
        stringResource(R.string.language_portugues),
        stringResource(R.string.language_esperanto)
    )
    val lang = LocaleManager.getLanguage()
    val index = language_codes.indexOf(lang)
    var selectedLanguageCode by remember { mutableStateOf(LocaleManager.getLanguage()) }
    val currentActivity = LocalContext.current as? Activity
    val onSelectedIndexChanged: (Int) -> Unit = { idx ->
        selectedLanguageCode = language_codes[idx]
        LocaleManager.setLocale(context, selectedLanguageCode)
        currentActivity?.recreate()
    }
    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages, index, onSelectedIndexChanged)
    //endregion

    ModalDrawerSheet(modifier = Modifier.width((LocalConfiguration.current.screenWidthDp * 0.8).dp)) {
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(200.dp)){
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.icon_400x400),
                    contentDescription = stringResource(id = R.string.icon),
                    modifier = Modifier.weight(1f),
                )
                Text("© 2023 CrowdWare", color=MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text("http://shift.crowdware.at", color=MaterialTheme.colorScheme.onPrimary)
            }
        }
        Spacer(Modifier.height(12.dp))
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = item.text) },
                label = { Text(item.text) },
                selected = item.text == selectedItem.value,
                onClick = {
                    scope.launch { drawerState.close() }
                    selectedItem.value = item.text
                    navController.navigate(item.id)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
        DropDownListbox(
            label = stringResource(R.string.select_preferred_language),
            stateHolder = stateHolderLanguage,
            modifier = Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun DrawerPreview() {
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("Mate list") }
    DrawerComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            DrawerSheet(drawerState = rememberDrawerState(DrawerValue.Closed), navController, selectedItem)
        }
    }
}
