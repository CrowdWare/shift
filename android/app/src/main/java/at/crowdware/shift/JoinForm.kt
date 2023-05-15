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

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.recreate
import at.crowdware.shift.ui.widgets.DropDownListbox
import at.crowdware.shift.ui.widgets.readCountryData
import at.crowdware.shift.ui.widgets.rememberDropDownListboxStateHolder
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.logic.PersistanceManager

data class JoinData(val name: String, val friend: String, val country: Int)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinForm(joinSuccessful: MutableState<Boolean>, language: String) {
    var context = LocalContext.current.applicationContext
    var errorMessage by remember { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var friend by rememberSaveable { mutableStateOf("") }
    val countries = readCountryData(LocalContext.current.applicationContext)
    val stateHolderCountry = rememberDropDownListboxStateHolder(countries)

    val onJoinFailed: (String?) -> Unit = { message ->
        if (message != null)
            errorMessage = message
    }

    val onJoinSucceed: () -> Unit = {
        errorMessage = ""
        joinSuccessful.value = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.join_shift)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Row(Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = R.drawable.icon_400x400),
                contentDescription = stringResource(id = R.string.icon),
                modifier = Modifier.fillMaxHeight(),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.name_or_nickname)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = friend,
            onValueChange = { friend = it },
            label = { Text(stringResource(R.string.invitation_code)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DropDownListbox(
            label = stringResource(R.string.select_your_country),
            stateHolder = stateHolderCountry)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = errorMessage, color = Color.Red)
        Button(
            onClick = {
                Backend.createAccount(
                    context, name, friend,
                    stateHolderCountry.value, language, onJoinSucceed, onJoinFailed
                )
            },
        ) {
            Text(stringResource(R.string.button_join_the_shift))
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun JoinFormPreview()
{
    val joinSuccessful = remember { mutableStateOf(false) }
    JoinForm(joinSuccessful, "de")
}

