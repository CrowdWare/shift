package at.crowdware.shift

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MainPage() {
    val balance by remember { mutableStateOf(13768) }
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
                Row(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        NumberFormat.getNumberInstance(Locale("de", "DE")).apply {
                            maximumFractionDigits = 3
                        }.format(balance / 1000.0), fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 70.sp),
                        modifier = Modifier.align(Alignment.Bottom)
                    )
                    Text(
                        "ml", fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(start = 4.dp)
                    )
                }
                Text(
                    text = "LMC",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text("Start Scooping", style = TextStyle(fontSize = 20.sp))
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
            items(20) { index ->
                Row {
                    Column {
                        Text("30.05.202$index", style = TextStyle(fontSize = 18.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Liquid scooped", style = TextStyle(fontSize = 18.sp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize().padding(4.dp)
                    ) {
                        Text(
                            "10 l", style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /*TODO*/ }) {
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
