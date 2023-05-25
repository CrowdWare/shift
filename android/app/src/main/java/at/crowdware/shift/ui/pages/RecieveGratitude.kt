package at.crowdware.shift.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.crowdware.shift.R
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.viewmodels.ReceiveViewModel
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.HourMinutesPicker
import at.crowdware.shift.ui.widgets.ModalNavigationDrawer
import at.crowdware.shift.ui.widgets.NavigationItem
import at.crowdware.shift.ui.widgets.TotalDisplay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReceiveGratitude(viewModel: ReceiveViewModel) {
    viewModel.balance.value = (Backend.getBalance() / 1000UL) * 1000UL // that should round down to full liter


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(viewModel.balance.value, true)
        Spacer(modifier = Modifier.height(8.dp))
        HourMinutesPicker(viewModel.hours, viewModel.minutes, viewModel.total, viewModel.longNumber)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.longNumberText.value,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.End),
            onValueChange = { input ->
                viewModel.longNumberText.value = input
                // Filter the input to accept only digits
                val filteredInput = input.filter { it.isDigit() }
                if (filteredInput.isNotEmpty()) {
                    // Catch NumberFormatException in case the number is larger than Long.MAX_VALUE
                    try {
                        viewModel.longNumber.value = filteredInput.toULong()
                    } catch (e: NumberFormatException) {
                        // Handle the error if needed
                    }
                } else {
                    viewModel.longNumber.value = 0UL
                }
                viewModel.total.value = (viewModel.hours.value * 60 + viewModel.minutes.value).toULong() + viewModel.longNumber.value
            },
            label = { Text("Enter an amount to add to the time") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = viewModel.description.value,
            onValueChange = {viewModel.description.value = it} ,
            placeholder = {Text("Enter the purpose of the transaction")},
            singleLine = false,
            modifier = Modifier.weight(1f).fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TotalDisplay(viewModel.total.value)
        Spacer(modifier = Modifier.height(8.dp))
        if(viewModel.balance.value / 1000UL >= viewModel.total.value) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.total.value > 0UL){
                Text("Continue", style = TextStyle(fontSize = 20.sp))
            }
        } else {
            Button(onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                enabled = false){
                Text("Balance too low", style = TextStyle(fontSize = 20.sp))
            }
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
    val receiveViewModel = viewModel<ReceiveViewModel>()
    ModalNavigationDrawer(list, selectedItem){ ReceiveGratitude(receiveViewModel) }
}

