package at.crowdware.shift.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.R
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.HourMinutesPicker
import at.crowdware.shift.ui.widgets.ModalNavigationDrawer
import at.crowdware.shift.ui.widgets.NavigationItem
import at.crowdware.shift.ui.widgets.TotalDisplay

@Composable
fun ReceiveGratitude() {
    var balance by remember { mutableStateOf(Backend.getBalance()) }
    var hours = remember { mutableStateOf(0) }
    var minutes = remember { mutableStateOf(0) }
    var total = remember { mutableStateOf(0UL) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(balance, true)
        Spacer(modifier = Modifier.height(8.dp))
        HourMinutesPicker(hours, minutes, total)
        Spacer(modifier = Modifier.height(8.dp))
        TotalDisplay(total.value)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "",
            onValueChange = {} ,
            placeholder = {Text("Enter the purpose of the transaction")},
            singleLine = false,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text("Continue", style = TextStyle(fontSize = 20.sp))
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ReceiveGratitudePreview() {
    val selectedItem = remember { mutableStateOf("Home") }
    val list = mutableListOf(
        NavigationItem("home", Icons.Default.Home, stringResource(R.string.navigation_home)),
        NavigationItem("friendlist", Icons.Default.Face, stringResource(R.string.navigation_friendlist))
    )
    ModalNavigationDrawer(list, selectedItem){ ReceiveGratitude() }
}

