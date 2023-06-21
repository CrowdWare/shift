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
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import at.crowdware.shift.MainActivity
import at.crowdware.shift.R
import at.crowdware.shift.ui.theme.OnPrimary
import at.crowdware.shift.ui.theme.Primary
import at.crowdware.shift.ui.widgets.NavigationManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import lib.Lib.addPeerFromQRCode
import lib.Lib.getPeerQRCode

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddNearbyFriend(mainActivity: MainActivity) {
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        QRScanDialog(
            openDialog = openDialog.value,
            onDismiss = { openDialog.value = false },
            mainActivity = mainActivity
        )
    }
    var code = remember { mutableStateOf(getPeerQRCode()) }
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.you_have_not_entered_storj))
        pushStringAnnotation("URL", "https://www.storj.io/")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("https://www.storj.io/")
        }
        pop()
        append(stringResource(R.string.you_have_not_entered_storj_part2))
    }

    val urlAnnotation = "URL"
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val permissionState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.CAMERA,)
    )
    val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text.length < 300) {
                return
            }
            mainActivity.barcodeView.pause()
            val res = addPeerFromQRCode(result.text)
            openDialog.value = false
        }
    }
    mainActivity.barcodeView.decodeContinuous(callback)

    val barcodeView = remember { mainActivity.barcodeView }
    if (permissionState.allPermissionsGranted) {
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
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(R.string.add_nearby_friend_info),
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (code.value == "") {
            ClickableText(
                text = annotatedString,
                style = TextStyle(fontSize = 18.sp),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(urlAnnotation, offset, offset)
                        .firstOrNull()?.let { annotation ->
                            val url = annotation.item
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                }
            )
        } else {
            Barcode(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(300.dp)
                    .height(300.dp),
                resolutionFactor = 10,
                type = BarcodeType.QR_CODE,
                value = code.value
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (permissionState.allPermissionsGranted) {
                Button(onClick = { openDialog.value = true }) {
                    Text("Open Camera")
                }
            } else {
                CameraPermission(
                    multiplePermissionsState = permissionState,
                    mainActivity.barcodeView
                )
                Button(
                    onClick = { permissionState.launchMultiplePermissionRequest() },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        stringResource(R.string.button_grant_camera_permission),
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                NavigationManager.navigate("friendlist")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            ),
        ) {
            Text(stringResource(R.string.button_done), style = TextStyle(fontSize = 20.sp))
        }
    }
}



@Composable
fun QRScanDialog(openDialog: Boolean, onDismiss: () -> Unit, mainActivity: MainActivity) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(R.string.scan_the_qr_code_to_get_the_keys)) },
            text = {
                Column {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { mainActivity.root },
                    )
                }
            },
            confirmButton = { TextButton(onClick = onDismiss) { Text("OK") } },
            dismissButton = {}
        )
    }
}