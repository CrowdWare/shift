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
package at.crowdware.shift.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourMinutesPicker(
    hours: MutableState<Int>,
    minutes: MutableState<Int>,
    total: MutableState<Long>,
    longNumber: MutableState<Long>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Row (modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.width(8.dp))
            CircularValuePicker(
                modifier = Modifier.weight(0.5f),
                initialValue = hours.value,
                maxValue = 24,
                unit = "h",
                onPositionChange = { position ->
                    hours.value = position
                    total.value = (hours.value * 60 + minutes.value).toLong() + longNumber.value
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            CircularValuePicker(
                modifier = Modifier.weight(0.5f),
                initialValue = minutes.value / 5,
                maxValue = 12,
                stepSize = 5,
                unit = "m",
                onPositionChange = { position ->
                    minutes.value = position * 5
                    total.value = (hours.value * 60 + minutes.value).toLong() + longNumber.value
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun HourMinutesPickerPreview() {
    val hours = remember { mutableStateOf(3) }
    val minutes = remember { mutableStateOf(30) }
    val total = remember { mutableStateOf(360L) }
    val amount = remember { mutableStateOf(360L) }
    HourMinutesPicker(hours, minutes, total, amount)
}