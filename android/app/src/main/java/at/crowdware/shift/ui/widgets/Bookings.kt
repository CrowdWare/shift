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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import at.crowdware.shift.R
import at.crowdware.shift.logic.Transaction
import at.crowdware.shift.ui.pages.Lmp
import com.google.gson.Gson
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import java.time.format.DateTimeFormatter

import lib.Lib.InitialBooking
import lib.Lib.Scooped
import lib.Lib.Lmp
import lib.Lib.Lmr
import lib.Lib.getAgreementQRCodeForTransaction
import java.time.Instant
import java.time.ZoneId
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale

@Composable
fun Bookings(transactions: SnapshotStateList<Transaction>, modifier: Modifier) {
    val openDialog = remember { mutableStateOf(false) }
    val trans = remember { mutableStateOf(Transaction("",0,"", 0L, 1)) }
    if (openDialog.value) {
        QRCodeDialog(
            openDialog = openDialog.value,
            onDismiss = { openDialog.value = false },
            transaction = trans.value)
    }
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        items(transactions.size) { index ->
            val transaction = transactions[index]
            Row (modifier=Modifier.clickable {
                trans.value = transaction
                openDialog.value = true }) {
                Column {
                    Text(unixToDate(transaction.date),
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                val txt = when (transaction.typ) {
                    Scooped -> stringResource(R.string.transaction_liquid_scooped)
                    InitialBooking -> stringResource(R.string.transaction_initial_liquid)
                    Lmp -> transaction.purpose
                    Lmr -> transaction.purpose
                    else -> ""
                }
                Text(txt, style = TextStyle(fontSize = 18.sp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    Text(
                        "${transaction.amount} l", style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }
}

fun unixToDate(unixTimestamp: Long): String {
    val instant = Instant.ofEpochSecond(unixTimestamp)
    val date = Date.from(instant)
    val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault())
    return localDate.format(formatter)
}

@Composable
fun QRCodeDialog(openDialog: Boolean, transaction: Transaction, onDismiss: () -> Unit) {
    val amount = stringResource(R.string.amount)
    val from = stringResource(R.string.from)
    val date = stringResource(R.string.date)
    val purpose = stringResource(R.string.purpose)

    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(R.string.transaction)) },
            text = {
                Column {
                    val enc = getAgreementQRCodeForTransaction(transaction.pkey)
                    val tokens = enc.split("|")

                    when (tokens[1]) {
                        "NOT_FOUND" -> Text(stringResource(R.string.transaction_not_found))
                        else -> {
                            val gson = Gson()
                            val trans: Lmp = gson.fromJson(tokens[0],
                                at.crowdware.shift.ui.pages.Lmp::class.java
                            )
                            if (tokens[1] == "NOT LMP") {
                                Text("$amount: ${trans.Amount}")
                                if(trans.Typ == "1")
                                    Text("$purpose: " + stringResource(R.string.transaction_initial_liquid))
                                else if(trans.Typ == "2")
                                    Text("$purpose: Scooping")
                                else
                                    Text("$purpose: ${trans.Purpose}")
                                if(trans.Typ == "4")
                                    Text("$from: ${trans.From}")
                                Text("$date: ${trans.Date.substring(0,10)}")
                            } else {
                                Text(
                                    stringResource(R.string.let_the_receiver_scan_the_qr_code_to_finalize_the_transaction)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("$amount: ${trans.Amount}")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("$purpose: ${trans.Purpose}")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("$date: ${trans.Date.substring(0,10)}")
                                Spacer(modifier = Modifier.width(16.dp))
                                Barcode(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(300.dp),
                                    resolutionFactor = 10,
                                    type = BarcodeType.QR_CODE,
                                    value = tokens[1]
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = onDismiss) { Text("OK") } },
            dismissButton = {}
        )
    }
}