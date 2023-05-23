package at.crowdware.shift.ui.widgets

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.R
import at.crowdware.shift.logic.Transaction
import at.crowdware.shift.logic.TransactionType
import java.time.format.DateTimeFormatter

@Composable
fun Bookings(transactions: SnapshotStateList<Transaction>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        items(transactions.size) { index ->
            val transaction = transactions[index]
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
                val txt = when (transaction.type) {
                    TransactionType.SCOOPED -> stringResource(R.string.transaction_liquid_scooped)
                    TransactionType.INITIAL_BOOKING -> stringResource(R.string.transaction_initial_liquid)
                }
                Text(txt, style = TextStyle(fontSize = 18.sp))
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
}