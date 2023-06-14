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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import at.crowdware.shift.R
import at.crowdware.shift.ui.viewmodels.GiveViewModel
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.NavigationDrawer
import at.crowdware.shift.ui.widgets.NavigationItem
import at.crowdware.shift.ui.widgets.NavigationManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.ui.theme.OnPrimary
import at.crowdware.shift.ui.theme.Primary
import at.crowdware.shift.ui.theme.Tertiary
import at.crowdware.shift.ui.theme.TertiaryError
import at.crowdware.shift.ui.widgets.AutoSizeText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import it.warpmobile.scanner.BuildCameraUI
import com.google.gson.Gson
import lib.Lib.getBalance
import java.text.NumberFormat
import java.util.Locale

import lib.Lib.getTransactionFromQRCode

data class Lmp(val pubKey: String, val amount: Long, val purpose: String, val type:String, val from: String)

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun GiveGratitude(viewModel: GiveViewModel, isPreview: Boolean = false) {
    viewModel.balance.value = getBalance() * 1000

    var code by remember {
        mutableStateOf(if(isPreview) {"{\"pubKey\":\"1234567890abcedef\",\"amount\":450,\"purpose\":\"Haircut and Massage\",\"type\":\"LMP\",\"from\":\"Sender\"}"} else {""})
    }
    var showScanner by remember { mutableStateOf(!isPreview) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(viewModel.balance.value, true)
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))
        if(showScanner) {
            Text(
                stringResource(R.string.scan_the_qr_code_to_get_the_offer_from_the_receiver),
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            BuildCameraUI(closeScanListener = {
            }) { qrcode ->
                    code = getTransactionFromQRCode(qrcode)
                showScanner = false
            }
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            if(code == "FRAUD") {
                Card(
                    colors = CardDefaults.cardColors(
                    containerColor = TertiaryError)
                ) {
                    Text(
                        stringResource(R.string.receiver_app_not_original),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {NavigationManager.navigate("home") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Go Back", style = TextStyle(fontSize = 20.sp))
                }
            } else {
                val gson = Gson()
                val trans: Lmp = gson.fromJson(code, Lmp::class.java)
                Text(
                    stringResource(R.string.agree_to_the_proposal_to_start_the_transaction),
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Tertiary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.purpose),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Text(
                        text = trans.purpose,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.from),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Text(
                        text = trans.from,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            stringResource(R.string.amount), fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.align(Alignment.TopStart)
                        )
                        AutoSizeText(
                            NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                                maximumFractionDigits = 3
                            }.format(trans.amount.toDouble()),
                            style = TextStyle(fontSize = 70.sp, fontWeight = FontWeight.Bold),
                        )
                        Text(
                            "LMC (liter)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (viewModel.balance.value / 1000L >= trans.amount) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = OnPrimary
                        ),
                        onClick = {/*
                            Backend.addTransactionToTrustChain(
                                trans.amount.toLong(),
                                TransactionType.LMP,
                                trans.purpose,
                                trans.from,
                                //trans.pubKey.hexToBytes()
                            )*/
                            NavigationManager.navigate("home")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.button_agree),
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                } else {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = OnPrimary
                        ),
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    ) {
                        Text(
                            stringResource(R.string.your_balance_is_below_the_amount_ordered),
                            color = Color.Red,
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GiveGratitudePreview() {
    val selectedItem = remember { mutableStateOf("Home") }
    val list = mutableListOf(
        NavigationItem("home", Icons.Default.Home, stringResource(R.string.navigation_home)),
        NavigationItem("friendlist", Icons.Default.Face, stringResource(R.string.navigation_friendlist))
    )
    val giveViewModel = viewModel<GiveViewModel>()
    giveViewModel.balance.value = 678000L
    NavigationDrawer(list, selectedItem){ GiveGratitude(giveViewModel, true) }
}