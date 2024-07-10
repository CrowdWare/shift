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

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.crowdware.shift.R
import at.crowdware.shift.logic.evaluateExpression
import at.crowdware.shiftapi.ui.theme.OnPrimary
import at.crowdware.shiftapi.ui.theme.Primary
import at.crowdware.shift.ui.viewmodels.ReceiveViewModel
import at.crowdware.shift.ui.widgets.BalanceDisplay
import at.crowdware.shift.ui.widgets.HourMinutesPicker
import at.crowdware.shift.ui.widgets.NavigationDrawer
import at.crowdware.shift.ui.widgets.NavigationItem
import at.crowdware.shift.ui.widgets.NavigationManager
import at.crowdware.shift.ui.widgets.TotalDisplay

import lib.Lib.getBalanceInMillis

@Composable
fun ReceiveGratitude(viewModel: ReceiveViewModel) {
    viewModel.balance.value = getBalanceInMillis()

    val scrollState = rememberScrollState()
    var message = remember { mutableStateOf("") }
    var purpose_needed = stringResource(R.string.the_purpose_needs_to_be_entered)

    Column(
        modifier = Modifier.verticalScroll(scrollState)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceDisplay(viewModel.balance.value,  displayBalanceOnly = true)
        Spacer(modifier = Modifier.height(8.dp))
        HourMinutesPicker(
            viewModel.hours,
            viewModel.minutes,
            viewModel.total,
            viewModel.longNumber,
            modifier = Modifier.focusable()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TotalDisplay(viewModel.total.value)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.longNumberText.value,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.End),
            singleLine = true,
            onValueChange = { input ->
                viewModel.longNumberText.value = input

                // Filter the input to accept only digits
                //val filteredInput = input.filter { it.isDigit() }
                //if (filteredInput.isNotEmpty()) {
                    // Catch NumberFormatException in case the number is larger than Long.MAX_VALUE
                    try {
                        //viewModel.longNumber.value = filteredInput.toLong()
                        viewModel.longNumber.value = evaluateExpression(input)
                    } catch (e: NumberFormatException) {
                        // Handle the error if needed
                    }
                //} else {
                //    viewModel.longNumber.value = 0L
                //}

                viewModel.total.value =
                    (viewModel.hours.value * 60 + viewModel.minutes.value).toLong() + viewModel.longNumber.value
            },
            label = { Text(stringResource(R.string.additional_amount)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = viewModel.description.value,
            onValueChange = { viewModel.description.value = it },
            label = { Text(stringResource(R.string.purpose)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(message.value, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (viewModel.description.value.isNullOrEmpty()) {
                    message.value = purpose_needed
                } else {
                NavigationManager.navigate("receive_gratitude_qrcode") }},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            ),
            enabled = viewModel.total.value > 0L
        ) {
            Text(stringResource(R.string.button_continue), style = TextStyle(fontSize = 20.sp))
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ReceiveGratitudePreview() {
    val selectedItem = remember { mutableStateOf("Home") }
    val list = mutableListOf(
        NavigationItem("home", Icons.Default.Home, stringResource(R.string.navigation_home)),
    )
    val receiveViewModel = viewModel<ReceiveViewModel>()
    NavigationDrawer(list, selectedItem, "SHIFT"){ ReceiveGratitude(receiveViewModel) }
}

