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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import at.crowdware.shift.logic.getTransactionsFromJSON
import at.crowdware.shiftapi.ui.theme.OnPrimary
import at.crowdware.shiftapi.ui.theme.OnSecondary
import at.crowdware.shiftapi.ui.theme.Primary
import at.crowdware.shiftapi.ui.theme.Secondary
import at.crowdware.shift.ui.widgets.NavigationItem
import kotlinx.coroutines.delay
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.Bookings
import at.crowdware.shift.ui.widgets.NavigationManager

import lib.Lib.startScooping
import lib.Lib.isScooping
import lib.Lib.getBalanceInMillis
import lib.Lib.getScoopedBalance
import lib.Lib.getScoopingHours
import lib.Lib.getTransactions
import lib.Lib.getUuid
import lib.Lib.getEncodedUuid

@Composable
fun ScoopPage(isPreview: Boolean = false) {
    var errorMessage by remember { mutableStateOf("") }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            stringResource(
                id = R.string.invite_message,
                stringResource(id = R.string.website_url),
                getUuid(),
                getEncodedUuid()
            )
        )
        type = "text/plain"
    }

    val transactions =
        remember { mutableStateListOf(*getTransactionsFromJSON(getTransactions()).toTypedArray()) }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    var balance by remember { mutableStateOf(getBalanceInMillis()) }
    var scooped by remember { mutableStateOf(getScoopedBalance()) }
    var scoopingHours by remember { mutableStateOf(getScoopingHours()) }
    var isScooping by remember { mutableStateOf(isScooping()) }
    var displayBalanceOnly by remember { mutableStateOf(!isScooping) }
    val network_error = stringResource(R.string.a_network_error_occurred)

    if (isPreview) {
        isScooping = true
    }

    LaunchedEffect(true) {
        while (true) {
            isScooping = isScooping()
            displayBalanceOnly = !isScooping
            if (isScooping) {
                balance = getBalanceInMillis()
                scooped = getScoopedBalance()
                scoopingHours = getScoopingHours()
                transactions.clear()
                for (t in getTransactionsFromJSON(getTransactions())) {
                    transactions.add(t)
                }
            }
            delay(3000L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(balance, scooped, scoopingHours, displayBalanceOnly)
        Text(errorMessage, color = Color.Red)

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { NavigationManager.navigate("receive_gratitude") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    stringResource(R.string.button_receive),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { NavigationManager.navigate("give_gratitude") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Secondary,
                    contentColor = OnSecondary
                )
            ) {
                Text(stringResource(R.string.button_give), style = TextStyle(fontSize = 20.sp))
            }
        }

        if (!isScooping) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                ),
                onClick = {
                    val res = startScooping()
                    when (res) {
                        0L -> isScooping = true
                        else -> errorMessage = network_error
                    }
                },
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            ),
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
    NavigationDrawer(list, selectedItem, "SHIFT"){ ScoopPage(true) }
}