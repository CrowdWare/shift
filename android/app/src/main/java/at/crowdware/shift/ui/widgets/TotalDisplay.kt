package at.crowdware.shift.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.ui.theme.Tertiary
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TotalDisplay(total: Long) {
    Card(modifier = Modifier.fillMaxWidth().height(120.dp), colors = CardDefaults.cardColors(
        containerColor = Tertiary,
    )) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Total", fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.align(Alignment.TopStart)
            )
            AutoSizeText(
                NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                    maximumFractionDigits = 3
                }.format(total.toDouble()),
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
}

@Preview
@Composable
fun TotalDisplayPreview() {
    val total = remember { mutableStateOf(240L) }
    TotalDisplay(total.value)
}