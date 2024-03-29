package at.crowdware.shift.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.R
import at.crowdware.shift.logic.PersistanceManager
import at.crowdware.shiftapi.ui.widgets.AutoSizeText
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BalanceDisplay(balance: Long, scooped: Long = 0L, scoopingHours: Double = 0.00, displayBalanceOnly: Boolean = false, test:Boolean = false) {
    val context = LocalContext.current
    var initValue = !displayBalanceOnly
    if(!displayBalanceOnly) {
        initValue = PersistanceManager.getDisplayScooping(context)
    }
    var displayBalance by remember { mutableStateOf(initValue) }
    if (test) {
        displayBalance = true
        initValue = true
    }
    val toogle_on = painterResource(id = R.drawable.toogle_on)
    val toogle_off = painterResource(id = R.drawable.toogle_off)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .let {
                if (!displayBalanceOnly) {
                    it.clickable() {
                        displayBalance = !displayBalance
                        PersistanceManager.setDisplayScooping(context, displayBalance)
                    }
                } else {
                    it
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize().padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if(displayBalance){
                stringResource(R.string.scooping_since) + " " + NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                    maximumFractionDigits = 2
                }.format(scoopingHours) + " / 20 h"} else {
                    stringResource(R.string.balance)}, fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.align(Alignment.TopStart)
            )
            if(displayBalance) {
                Image(
                    painter = toogle_on,
                    contentDescription = "SVG Drawable",
                    modifier = Modifier.align(Alignment.BottomStart).size(40.dp)
                )
            } else {
                if(!displayBalanceOnly) {
                    Image(
                        painter = toogle_off,
                        contentDescription = "SVG Drawable",
                        modifier = Modifier.align(Alignment.BottomStart).size(40.dp)
                    )
                }
            }

            AutoSizeText(
                if (displayBalance) {
                    NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                        maximumFractionDigits = 3
                    }.format(scooped.toDouble())
                } else {
                    NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                        maximumFractionDigits = 0
                    }.format((balance.toDouble() / 1000.0f))
                },
                style = TextStyle(fontSize = 70.sp, fontWeight = FontWeight.Bold),
            )
            Text(
                text = if (displayBalance) {
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
    BalanceDisplay(balance = 13000L , scooped = 5500L, scoopingHours = 10.565577354, test=true)
}