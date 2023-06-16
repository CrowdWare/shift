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

import android.Manifest
import androidx.compose.runtime.Composable
import at.crowdware.shift.R
import at.crowdware.shift.ui.viewmodels.GiveViewModel
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.NavigationManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import at.crowdware.shift.MainActivity
import at.crowdware.shift.ui.theme.OnPrimary
import at.crowdware.shift.ui.theme.Primary
import at.crowdware.shift.ui.theme.Tertiary
import at.crowdware.shift.ui.theme.TertiaryError
import at.crowdware.shift.ui.widgets.AutoSizeText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.gson.Gson
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import lib.Lib.getBalanceInMillis
import java.text.NumberFormat
import java.util.Locale

import lib.Lib.getTransactionFromQRCode
import lib.Lib.acceptProposal

data class Lmr(val pubKey: String, val amount: Long, val purpose: String, val type:String, val from: String)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanAgreement(viewModel: GiveViewModel, mainActivity: MainActivity) {

    viewModel.balance.value = getBalanceInMillis()

    var code by remember { mutableStateOf("") }
    var showScanner by remember { mutableStateOf(true) }

    val permissionState = rememberMultiplePermissionsState(
        listOf<String>(
            Manifest.permission.CAMERA,
        )
    )

    val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null) {
                return
            }
            mainActivity.barcodeView.pause()
            code = getTransactionFromQRCode(result.text)
            showScanner = false
        }
    }

    mainActivity.barcodeView.decodeContinuous(callback)
    val barcodeView = remember { mainActivity.barcodeView }
    if(permissionState.allPermissionsGranted) {
        DisposableEffect(Unit) {
            barcodeView.resume()
            onDispose {
                barcodeView.pause()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(viewModel.balance.value, true)
        Spacer(modifier = Modifier.height(8.dp))

        if (showScanner) {
            Text(
                stringResource(R.string.scan_the_qr_code_to_finalize_the_transaction),
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            if(permissionState.allPermissionsGranted) {
                AndroidView(modifier = Modifier.fillMaxSize(), factory = { mainActivity.root })
            } else {
                CameraPermission(multiplePermissionsState = permissionState, mainActivity.barcodeView)
                Button(
                    onClick = { permissionState.launchMultiplePermissionRequest() },
                    modifier = Modifier.fillMaxWidth(),) {
                    Text("Grant Camera Permission",style = TextStyle(fontSize = 20.sp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            if (code == "FRAUD") {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = TertiaryError
                    )
                ) {
                    Text(
                        stringResource(R.string.giver_app_not_original),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { NavigationManager.navigate("home") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go Back", style = TextStyle(fontSize = 20.sp))
                }
            } else {
                val gson = Gson()
                val trans: Lmr = gson.fromJson(code, Lmr::class.java)
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
                Text(
                    stringResource(R.string.press_button_to_book_in_the_amount),
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    ),
                    onClick = {
                        acceptProposal()
                        NavigationManager.navigate("home")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.button_book),
                        style = TextStyle(fontSize = 20.sp)
                    )
                }

            }
        }
    }
}