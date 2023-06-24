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
package at.crowdware.shift.ui.pages

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.R
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.PersistanceManager
import at.crowdware.shiftapi.ui.theme.OnPrimary
import at.crowdware.shiftapi.ui.theme.Primary
import at.crowdware.shiftapi.ui.widgets.DropDownListbox
import at.crowdware.shiftapi.ui.widgets.rememberDropDownListboxStateHolder

@SuppressLint("FrequentlyChangedStateReadInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Intro(hasSeenDeleteWarning: MutableState<Boolean>) {
    val context = LocalContext.current.applicationContext
    val scrollState = rememberLazyListState()
    val currentActivity = LocalContext.current as? Activity
    val languages = LocaleManager.getLanguages()
    val lang = LocaleManager.getLanguage()
    val index = LocaleManager.getLanguageIndex()
    var selectedLanguageCode by remember { mutableStateOf(lang) }
    val onSelectedIndexChanged: (Int) -> Unit = { idx ->
        LocaleManager.setLocale(context,idx)
        selectedLanguageCode = LocaleManager.getLanguage()
        currentActivity?.recreate()
    }

    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages, index, onSelectedIndexChanged)
    val hasSeenAll = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.intro)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Primary,
                titleContentColor = OnPrimary,
                navigationIconContentColor = OnPrimary,
                actionIconContentColor = OnPrimary,
            )
        )
        Row(Modifier.height(150.dp)) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = stringResource(id = R.string.icon),
                modifier = Modifier.fillMaxHeight(),
            )
        }
        LazyColumn(modifier = Modifier.weight(1f), state = scrollState) {
            items(2) { index ->
                Text(
                    if (index == 0) { stringResource(R.string.uninstall_warning) } else {""},
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
            }
        }
        if( scrollState.layoutInfo.visibleItemsInfo.size < 2) {
            Text("...", style = TextStyle(fontSize = 20.sp))
        } else { hasSeenAll.value = true}
        Spacer(modifier = Modifier.height(16.dp))
        DropDownListbox(
            label = stringResource(R.string.select_preferred_language),
            stateHolder = stateHolderLanguage)
        Spacer(modifier = Modifier.height(16.dp))
        Button( colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary
        ),onClick = {
            PersistanceManager.putHasSeenDeleteWarning(context)
            hasSeenDeleteWarning.value = true
        },
        enabled = selectedLanguageCode.isNotEmpty() && hasSeenAll.value) {
            Text(if (hasSeenAll.value) { stringResource(R.string.button_continue)} else { stringResource(
                R.string.button_read_all
            ) })
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun IntroPreview()
{
    val hasSeenDeleteWarning = remember { mutableStateOf(false) }
    Intro(hasSeenDeleteWarning)
}
