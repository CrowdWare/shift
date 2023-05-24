package at.crowdware.shift.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourMinutesPicker(
    hours: MutableState<Int>,
    minutes: MutableState<Int>,
    total: MutableState<ULong>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Row (modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.width(8.dp))
            CircularValuePicker(
                modifier = Modifier.size(250.dp).weight(0.5f),
                initialValue = hours.value,
                maxValue = 12,
                unit = "h",
                onPositionChange = { position ->
                    hours.value = position
                    total.value = (hours.value * 60 + minutes.value).toULong()
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            CircularValuePicker(
                modifier = Modifier.size(250.dp).weight(0.5f),
                initialValue = minutes.value,
                maxValue = 12,
                stepSize = 5,
                unit = "m",
                onPositionChange = { position ->
                    minutes.value = position * 5
                    total.value = (hours.value * 60 + minutes.value).toULong()
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
    val minutes = remember { mutableStateOf(30 / 5) }
    val total = remember { mutableStateOf(360UL) }
    HourMinutesPicker(hours, minutes, total)
}