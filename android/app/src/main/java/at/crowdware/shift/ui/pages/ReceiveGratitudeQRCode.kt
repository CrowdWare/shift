package at.crowdware.shift.ui.pages

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.crowdware.shift.R
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.viewmodels.ReceiveViewModel
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.ModalNavigationDrawer
import at.crowdware.shift.ui.widgets.NavigationItem
import at.crowdware.shift.ui.widgets.NavigationManager
import at.crowdware.shift.ui.widgets.TotalDisplay
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import nl.tudelft.ipv8.IPv8
import nl.tudelft.ipv8.android.IPv8Android

@Composable
fun ReceiveGratitudeQRCode(viewModel: ReceiveViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        TotalDisplay(viewModel.total.value)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.let_the_giver_scan_the_qr_code_to_start_the_transaction),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 20.sp)
        )

        val map = mutableMapOf("type" to "LMP")  // liquid micro payment
        map["pubKey"] = IPv8Android.getInstance().myPeer.publicKey.toString()
        map["amount"] = viewModel.total.value.toString()
        map["name"] = Backend.getAccount().name
        map["description"] = viewModel.description.value
        val mapString = map.toString()

        Barcode(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .width(300.dp)
                .height(300.dp),
            resolutionFactor = 10,
            type = BarcodeType.QR_CODE,
            value = mapString
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.reset()
                NavigationManager.navigate("home") },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.total.value > 0UL
        ) {
            Text(stringResource(R.string.button_done), style = TextStyle(fontSize = 20.sp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ReceiveGratitudeQRCodePreview() {
    val selectedItem = remember { mutableStateOf("Home") }
    val list = mutableListOf(
        NavigationItem("home", Icons.Default.Home, stringResource(R.string.navigation_home)),
        NavigationItem("friendlist", Icons.Default.Face, stringResource(R.string.navigation_friendlist))
    )
    val receiveViewModel = viewModel<ReceiveViewModel>()
    receiveViewModel.total.value = 580UL
    ModalNavigationDrawer(list, selectedItem){ ReceiveGratitudeQRCode(receiveViewModel) }
}