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
import at.crowdware.shift.ui.theme.Tertiary
import at.crowdware.shift.ui.widgets.AutoSizeText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import it.warpmobile.scanner.BuildCameraUI
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.NumberFormat
import java.util.Locale

data class Lmp(val pubKey: String, val amount: ULong, val description: String, val type:String)

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun GiveGratitude(viewModel: GiveViewModel, isPreview: Boolean = false) {
    viewModel.balance.value = Backend.getBalance()

    var code by remember {
        mutableStateOf(if(isPreview) {"{\"pubKey\":\"1234567890abcedef\",\"amount\":450,\"description\":\"Haircut and Massage\",\"type\":\"LMP\"}"} else {""})
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
                "Scan the QR code to get the offer from the receiver.",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            BuildCameraUI(closeScanListener = {
            }) { qrcode ->
                code = qrcode
                showScanner = false
            }
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            val gson = Gson()
            val trans: Lmp = gson.fromJson(code, Lmp::class.java)
            Text(
                "Agree to the proposal to start the transaction.",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(colors = CardDefaults.cardColors(
                containerColor = Tertiary)
            ){
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth().padding(4.dp)
                )
                Text(
                    text = trans.description,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth().padding(4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Amount", fontWeight = FontWeight.Bold,
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
            if(viewModel.balance.value / 1000UL >= trans.amount) {
                Button(
                    onClick = { NavigationManager.navigate("home") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agree", style = TextStyle(fontSize = 20.sp))
                }
            } else {
                Button(
                    onClick = { NavigationManager.navigate("home") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                ) {
                    Text("You balance is below the amount ordered.", color= Color.Red,style = TextStyle(fontSize = 20.sp))
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
    giveViewModel.balance.value = 678000UL
    ModalNavigationDrawer(list, selectedItem){ GiveGratitude(giveViewModel, true) }
}