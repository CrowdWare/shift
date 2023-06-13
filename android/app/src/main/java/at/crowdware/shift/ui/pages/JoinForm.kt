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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontLoadingStrategy.Companion.Blocking
import androidx.compose.ui.unit.dp
import at.crowdware.shift.R
import at.crowdware.shift.ui.widgets.DropDownListbox
import at.crowdware.shift.ui.widgets.readCountryData
import at.crowdware.shift.ui.widgets.rememberDropDownListboxStateHolder
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.theme.OnPrimary
import at.crowdware.shift.ui.theme.Primary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import lib.Lib.createAccount
import java.util.UUID
import java.util.concurrent.BlockingDeque
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinForm(joinSuccessful: MutableState<Boolean>, language: String) {
    val context = LocalContext.current
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
                containerColor = Primary,
                titleContentColor = OnPrimary,
                navigationIconContentColor = OnPrimary,
                actionIconContentColor = OnPrimary
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
            singleLine = true,
            label = { Text(stringResource(R.string.name_or_nickname)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = friend,
            onValueChange = { friend = it },
            singleLine = true,
            label = { Text(stringResource(R.string.invitation_code)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DropDownListbox(
            label = stringResource(R.string.select_your_country),
            stateHolder = stateHolderCountry)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = errorMessage, color = Color.Red)
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            ),
            onClick = {
                GlobalScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        createAccount(
                            name,
                            UUID.randomUUID().toString(),
                            friend,
                            stateHolderCountry.value,
                            language
                        )
                    }
                    if (result == 0L) {
                        withContext(Dispatchers.Main) {
                            onJoinSucceed()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            var msg = ""
                            when (result) {
                                1L -> msg = context.getString(R.string.please_enter_your_name)
                                2L -> msg = context.getString(R.string.please_enter_the_invitation_code)
                                3L -> msg = context.getString(R.string.please_select_your_country)
                                5L -> msg = context.getString(R.string.a_network_error_occurred)
                                6L -> msg = context.getString(R.string.a_network_error_occurred)
                            }
                            onJoinFailed(msg)
                        }
                    }
                }
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

