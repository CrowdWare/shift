package at.crowdware.shift.ui.pages

import androidx.compose.runtime.Composable
import at.crowdware.shift.R
import at.crowdware.shift.ui.viewmodels.GiveViewModel
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.ModalNavigationDrawer
import at.crowdware.shift.ui.widgets.NavigationItem
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.widgets.NavigationManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import it.warpmobile.scanner.BuildCameraUI


@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun GiveGratitude(viewModel: GiveViewModel) {
    viewModel.balance.value = Backend.getBalance()

    var code by remember {
        mutableStateOf("Test")
    }
    var showScanner by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(viewModel.balance.value, true)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Scan the QR code and accept the offer to give gratitude.",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if(showScanner) {
            BuildCameraUI(closeScanListener = {
            }) { qrcode ->
                code = qrcode
                showScanner = false
            }
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Text(
                text = code,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { NavigationManager.navigate("home") }) {
                Text("Agree", style = TextStyle(fontSize = 20.sp))
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
    giveViewModel.balance.value = 678000UL
    ModalNavigationDrawer(list, selectedItem){ GiveGratitude(giveViewModel) }
}