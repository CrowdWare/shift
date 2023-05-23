package at.crowdware.shift.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    var showTimePicker by remember { mutableStateOf(false) }
    if (showTimePicker) {
        TimePickerDialog(onDismissRequest = { showTimePicker = false },
            onTimeChange = {
                hours.value = it.hour
                minutes.value = it.minute
                total.value = (hours.value * 60 + minutes.value).toULong()
                showTimePicker = false},
            is24HourFormat = true,
            initialTime = LocalTime.of(hours.value,minutes.value,0))
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp).clickable { showTimePicker = true }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                "Time", fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.align(Alignment.TopStart)
            )
            Row(modifier = Modifier.align(Alignment.Center).padding(8.dp)) {
                Text(hours.value.toString(), fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 70.sp, textAlign = TextAlign.Right), modifier = Modifier.alignByBaseline())
                Spacer(modifier = Modifier.width(8.dp))
                Text("h", style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Left), modifier = Modifier.alignByBaseline())
                Spacer(modifier = Modifier.width(32.dp))
                Text(minutes.value.toString(), fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 70.sp, textAlign = TextAlign.Right), modifier = Modifier.alignByBaseline())
                Spacer(modifier = Modifier.width(8.dp))
                Text("min", style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Left), modifier = Modifier.alignByBaseline())
            }
        }
    }
}

@Preview
@Composable
fun HourMinutesPickerPreview() {
    val hours = remember { mutableStateOf(8) }
    val minutes = remember { mutableStateOf(30) }
    val total = remember { mutableStateOf(360UL) }
    HourMinutesPicker(hours, minutes, total)
}