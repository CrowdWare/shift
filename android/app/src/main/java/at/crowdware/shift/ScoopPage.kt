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
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.navigation.compose.rememberNavController
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.logic.TransactionType
import at.crowdware.shift.ui.widgets.AutoSizeText
import at.crowdware.shift.ui.widgets.NavigationItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import org.w3c.dom.Text
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScoopPage() {
    var displayMilliliter by remember { mutableStateOf(true) }
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
    val application: Application = LocalContext.current.applicationContext as Application
    var balance by remember { mutableStateOf(Backend.getBalance()) }
    var isScooping by remember { mutableStateOf(Backend.getAccount().isScooping) }

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
            delay(1000L)
        }
    }
    ServiceStartRequest(
        openDialog = openDialog.value,
        onDismiss = { openDialog.value = false },
        onConfirm = {
            openDialog.value = false
            Backend.startScooping(application)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clickable(onClick = { displayMilliliter = !displayMilliliter })
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.balance), fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.align(Alignment.TopStart)
                )
                AutoSizeText(
                    if (displayMilliliter) {
                        NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                            maximumFractionDigits = 3
                        }.format(balance.toDouble())
                    } else {
                        NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                            maximumFractionDigits = 0
                        }.format((balance.toDouble() / 1000.0f))
                    },
                    style = TextStyle(fontSize = 70.sp, fontWeight = FontWeight.Bold),
                )
                Text(
                    text = if (displayMilliliter) {
                        "LMC (ml)"
                    } else {
                        "LMC (liter)"
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
        Text(errorMessage, color = Color.Red)
        if(!Backend.getAccount().isScooping) {
            Button(
                onClick = { openDialog.value = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isScooping
            ) {
                Text(
                    if (isScooping) {
                        stringResource(R.string.button_scooping_started)
                    } else {
                        stringResource(R.string.button_start_scooping)
                    }, style = TextStyle(fontSize = 20.sp)
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
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            items(transactions.size) { index ->
                val transaction = transactions[index]
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                Row {
                    Column {

                        Text(
                            transaction.date.format(formatter),
                            style = TextStyle(fontSize = 18.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    val txt = when (transaction.type) {
                        TransactionType.SCOOPED -> stringResource(R.string.transaction_liquid_scooped)
                        TransactionType.INITIAL_BOOKING -> stringResource(R.string.transaction_initial_liquid)
                    }
                    Text(txt, style = TextStyle(fontSize = 18.sp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                    ) {
                        Text(
                            "${transaction.amount / 1000u} l", style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
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
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("Home") }
    val list = mutableListOf(
        NavigationItem(Icons.Default.Home, stringResource(R.string.navigation_home), "home", null, 0),
        NavigationItem(Icons.Default.Face, stringResource(R.string.navigation_friendlist), "friendlist", null, 0)
    )
    ModalNavigationDrawer(navController = navController, list, selectedItem){ ScoopPage()}
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
            dismissButton = { TextButton(onClick = onDismiss ) { Text("Dismiss") } }
        )
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewDialog() {
    val openDialog = remember { mutableStateOf(false) }
    ServiceStartRequest(
        openDialog = openDialog.value,
        onDismiss = { openDialog.value = false },
        onConfirm = { openDialog.value = false })
}
