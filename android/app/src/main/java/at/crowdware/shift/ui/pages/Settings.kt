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

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shiftapi.ui.widgets.DropDownListbox
import at.crowdware.shiftapi.ui.widgets.rememberDropDownListboxStateHolder
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import at.crowdware.shift.R
import at.crowdware.shiftapi.ui.theme.OnPrimary
import at.crowdware.shiftapi.ui.theme.Primary
import at.crowdware.shift.ui.widgets.NavigationManager
import lib.Lib.getAccessToken
import lib.Lib.getBucketName
import lib.Lib.getName
import lib.Lib.setName
import lib.Lib.setStorj


@Composable
fun Settings() {
    val name = remember { mutableStateOf(getName()) }
    val bucketName = remember { mutableStateOf(getBucketName()) }
    val accessToken = remember { mutableStateOf(getAccessToken()) }
    var saveButtonEnabled by remember { mutableStateOf(false) }
    var nameChanged = remember { mutableStateOf(false) }
    var storjChanged = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    //region vars for the DropDownlistbox
    val context = LocalContext.current
    val languages = LocaleManager.getLanguages()
    val index = LocaleManager.getLanguageIndex()
    val currentActivity = LocalContext.current as? Activity
    val onSelectedIndexChanged: (Int) -> Unit = { idx ->
        LocaleManager.setLocale(context, idx)
        currentActivity?.recreate()
    }
    val stateHolderLanguage = rememberDropDownListboxStateHolder(languages, index, onSelectedIndexChanged)
    //endregion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.personal_data), fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropDownListbox(
            label = stringResource(R.string.select_preferred_language),
            stateHolder = stateHolderLanguage,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = name.value,
            label = {Text(stringResource(R.string.name_or_nickname),)},
            onValueChange = {it ->
                name.value = it
                nameChanged.value = true
                saveButtonEnabled = true
        })

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Storj", fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = bucketName.value,
            label = {Text("Bucket Name",)},
            onValueChange = {it ->
                bucketName.value = it
                storjChanged.value = true
                saveButtonEnabled = true
        })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = accessToken.value,
            label = {Text("Access Token",)},
            onValueChange = {it ->
                accessToken.value = it
                storjChanged.value = true
                saveButtonEnabled = true
        })
        Spacer(modifier = Modifier.height(8.dp))
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary
        ),enabled = saveButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if(nameChanged.value)
                    setName(name.value)
                if(storjChanged.value)
                    setStorj(bucketName.value, accessToken.value)
            saveButtonEnabled = false
        }) {
            Text(stringResource(R.string.save_changes))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary
        ),
            modifier = Modifier.fillMaxWidth(),
            onClick = { NavigationManager.navigate("plugin_settings") }) {
            Text(stringResource(R.string.plugin_settings))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview() {
    Settings()
}