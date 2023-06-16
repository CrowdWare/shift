package at.crowdware.shift.ui.widgets

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.R
import at.crowdware.shift.logic.PersistanceManager
import java.text.NumberFormat
import java.util.Locale


@Composable
fun BalanceDisplay(balance: Long, scooped: Long, displayLiterOnly: Boolean = false) {
    val context = LocalContext.current
    var initValue = !displayLiterOnly
    if(!displayLiterOnly) {
        initValue = PersistanceManager.getDisplayMillis(context)
    }
    var displayMilliliter by remember { mutableStateOf(initValue) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .let {
                if (!displayLiterOnly) {
                    it.clickable() {
                        displayMilliliter = !displayMilliliter
                        PersistanceManager.setDisplayMillis(context, displayMilliliter)
                    }
                } else {
                    it
                }
            }
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
                    }.format(balance.toDouble() + scooped.toDouble())
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
}

@Preview
@Composable
fun BalanceDisplayPreview() {
    BalanceDisplay(balance = 13000L , scooped = 1000L)
}