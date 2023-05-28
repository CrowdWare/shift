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

import android.app.Application
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.ui.widgets.NavigationDrawer
import at.crowdware.shift.R
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.theme.OnSecondary
import at.crowdware.shift.ui.theme.Secondary
import at.crowdware.shift.ui.widgets.NavigationItem
import kotlinx.coroutines.delay
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.Bookings
import at.crowdware.shift.ui.widgets.HourMinutesPicker
import at.crowdware.shift.ui.widgets.NavigationManager
import at.crowdware.shift.ui.widgets.TotalDisplay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoopPage(isPreview: Boolean = false) {
    val errorMessage by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            stringResource(
                id = R.string.invite_message,
                stringResource(id = R.string.website_url),
                Backend.getAccount().uuid
            )
        )
        type = "text/plain"
    }
    val transactions = remember { mutableStateListOf(*Backend.getTransactions().toTypedArray()) }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    val application = LocalContext.current.applicationContext
    var balance by remember { mutableStateOf(Backend.getBalance()) }
    var isScooping by remember { mutableStateOf(Backend.getAccount().isScooping) }

    if(isPreview) {
        isScooping = true

    }
    LaunchedEffect(true) {
        while (true) {
            isScooping = Backend.getAccount().isScooping
            if (isScooping) {
                balance = Backend.getBalance()
                transactions.clear()
                for(t in Backend.getTransactions()) {
                    transactions.add(t)
                }
            }
            delay(3000L)
        }
    }
    ServiceStartRequest(
        openDialog = openDialog.value,
        onDismiss = { openDialog.value = false },
        onConfirm = {
            openDialog.value = false
            if(application is Application)
                Backend.startScooping(application)
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(balance)
        Text(errorMessage, color = Color.Red)
        if (isScooping) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { NavigationManager.navigate("receive_gratitude") },
                    modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.button_receive), style = TextStyle(fontSize = 20.sp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {NavigationManager.navigate("give_gratitude")},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Secondary,
                        contentColor = OnSecondary
                    )
                ) {
                    Text(stringResource(R.string.button_give), style = TextStyle(fontSize = 20.sp))
                }
            }

        } else {
            Button(
                onClick = { openDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.button_start_scooping),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.bookings), fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Bookings(transactions, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { context.startActivity(shareIntent) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.button_invite_friends),
                style = TextStyle(fontSize = 20.sp)
            )
        }
    }
}

@Preview(
    showSystemUi = true,)
@Composable
fun MainPagePreview() {
    val selectedItem = remember { mutableStateOf("Home") }
    val list = mutableListOf(
        NavigationItem("home", Icons.Default.Home, stringResource(R.string.navigation_home)),
        NavigationItem("friendlist", Icons.Default.Face, stringResource(R.string.navigation_friendlist))
    )
    NavigationDrawer(list, selectedItem){ ScoopPage(true) }
}

@Composable
fun ServiceStartRequest(openDialog: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(id = R.string.button_start_scooping)) },
            text = {
                Text(
                    stringResource(R.string.servive_start_request_message)
                )
            },
            confirmButton = { TextButton(onClick = onConfirm ) { Text("OK") } },
            dismissButton = { TextButton(onClick = onDismiss ) { Text(stringResource(R.string.dismiss)) } }
        )
    }
}
