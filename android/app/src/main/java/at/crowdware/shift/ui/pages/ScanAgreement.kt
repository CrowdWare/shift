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

import lib.Lib.getProposalFromQRCode
import lib.Lib.acceptProposal
import lib.Lib.getAgreementFromQRCode

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
            if (result.text == null || result.text.length < 300) {
                return
            }
            mainActivity.barcodeView.pause()
            code = getAgreementFromQRCode(result.text)
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
        BalanceDisplay(viewModel.balance.value, displayBalanceOnly = true)
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    )) {
                    Text(stringResource(R.string.button_grant_camera_permission),style = TextStyle(fontSize = 20.sp))
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
            } else if (code == "WRONG_TYP") {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = TertiaryError
                    )
                ) {
                    Text(
                        stringResource(R.string.you_have_scanned_a_wrong_qrcode),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.reset()
                        NavigationManager.navigate("home") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go Back", style = TextStyle(fontSize = 20.sp))
                }
            } else if (code == "DOUBLE_SPENT") {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = TertiaryError
                    )
                ) {
                    Text(
                        stringResource(R.string.qrcode_allread_booked),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.reset()
                        NavigationManager.navigate("home") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go Back", style = TextStyle(fontSize = 20.sp))
                }
            } else {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Tertiary
                    )
                ) {
                    Text(
                        stringResource(R.string.transaction_has_been_booked),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary
                    ),
                    onClick = {
                        viewModel.reset()
                        NavigationManager.navigate("home")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.button_done),
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
            }
        }
    }
}