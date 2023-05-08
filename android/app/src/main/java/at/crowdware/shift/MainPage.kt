package at.crowdware.shift

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.ui.widgets.AutoSizeText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MainPage() {
    var displayMilliliter by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "SHIFT is a new app to create worldwide universal basic income. Get your first coins now, by following this link http://shift.crowdware.at Download the app, start it and use this id\n" + Backend.getAccount().uuid + " as invitation code."
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    var balance by remember { mutableStateOf(Backend.getBalance(context)) }
    var isScooping by remember { mutableStateOf(Backend.getAccount().scooping > 0u) }
    val onScoopingFailed: (String?) -> Unit = { message ->
        if (message != null)
            errorMessage = message
    }
    val onScoopingSucceed: () -> Unit = {
        isScooping = true
    }

    LaunchedEffect(true) {
        while (true) {
            balance = Backend.getBalance(context)
            isScooping = Backend.getAccount().scooping > 0u
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
                    "Balance", fontWeight = FontWeight.Bold,
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
        Button(
            onClick = {
                Backend.setScooping(context, onScoopingSucceed, onScoopingFailed)
                isScooping = true
                      },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isScooping
        ) {
            Text(if(isScooping){"Scooping started"}else{"Start Scooping"}, style = TextStyle(fontSize = 20.sp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Bookings", fontWeight = FontWeight.Bold,
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
            items(Backend.getAccount().transactions.size) { index ->
                val transaction = Backend.getAccount().transactions[index]
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
            Text("Invite Friends", style = TextStyle(fontSize = 20.sp))
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