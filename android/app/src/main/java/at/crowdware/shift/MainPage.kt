package at.crowdware.shift

import android.Manifest
import android.app.Application
import android.content.Context
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.navigation.compose.rememberNavController
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.logic.Network
import at.crowdware.shift.ui.widgets.AutoSizeText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.reflect.KFunction1

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BluetoothPermission(
    permissionState: PermissionState,
    isScooping: KFunction1<Boolean, Unit>,
    context: Context,
    onScoopingSucceed: () -> Unit,
    onScoopingFailed: (String?) -> Unit
) {
    PermissionRequired(
        permissionState =  permissionState,
        permissionNotGrantedContent = {  },
        permissionNotAvailableContent = { }
    ) {
        // TODO, das startet nun automatisch wenner permission hat
        //Backend.setScooping(context, onScoopingSucceed, onScoopingFailed)
        //isScooping(true)
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainPage() {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.BLUETOOTH_CONNECT)
    var displayMilliliter by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            stringResource(id = R.string.invite_message, stringResource(id = R.string.website_url), Backend.getAccount().uuid)
        )
        type = "text/plain"
    }
    val transactions = remember { mutableStateListOf(*Backend.getAccount().transactions.toTypedArray()) }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    var balance by remember { mutableStateOf(Backend.getBalance(context)) }
    var isScooping by remember { mutableStateOf(Backend.getAccount().scooping > 0u) }
    fun updateIsScooping(value: Boolean) {
        isScooping = value
    }
    val onScoopingFailed: (String?) -> Unit = { message ->
        if (message != null)
            errorMessage = message
    }
    val onScoopingSucceed: () -> Unit = {
        errorMessage = ""
        isScooping = true
    }
    if (permissionState.hasPermission && !Network.isStarted()) {
        Network.initIPv8(context.applicationContext as Application)
    }

    LaunchedEffect(true) {
        while (true) {
            isScooping = Backend.getAccount().scooping > 0u
            if(isScooping)
                balance = Backend.getBalance(context)
            delay(1000L)
        }
    }
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
        BluetoothPermission(permissionState = permissionState,
            ::updateIsScooping,
            context,
            onScoopingSucceed,
            onScoopingFailed)
        val shouldShowBluetoothOff = (permissionState.permissionRequested && !permissionState.shouldShowRationale && !permissionState.hasPermission)
        Button(
            onClick = { permissionState.launchPermissionRequest() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isScooping && !shouldShowBluetoothOff
        ) {
            Text(
                if(isScooping) {
                    stringResource(R.string.button_scooping_started)
                } else {
                if(shouldShowBluetoothOff) {
                    stringResource(R.string.bluetooth_disabled)
                } else {
                if(permissionState.shouldShowRationale) {
                    stringResource(R.string.bluetooth_rational)
                } else {
                    stringResource(R.string.button_start_scooping)
                }}}, style = if(shouldShowBluetoothOff) { TextStyle(fontSize = 15.sp, color = Color.DarkGray) } else { TextStyle(fontSize = 20.sp) }
            )
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
                    Text(transaction.description, style = TextStyle(fontSize = 18.sp))
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
            Text(stringResource(R.string.button_invite_friends), style = TextStyle(fontSize = 20.sp))
        }
    }
}

@Preview(
    showSystemUi = true,)
@Composable
fun MainPagePreview() {
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("Home") }
    ModalNavigationDrawer(navController = navController, selectedItem){ MainPage()}
}
