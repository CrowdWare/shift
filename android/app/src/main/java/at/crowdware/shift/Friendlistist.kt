package at.crowdware.shift

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
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
import at.crowdware.shift.logic.Friend
import java.time.format.DateTimeFormatter

@Composable
fun Friendlist() {
    val context = LocalContext.current
    val friendListState = remember { mutableStateOf(emptyList<Friend>()) }
    var errorMessage by remember { mutableStateOf("") }
    val onFriendlistFailed: (String?) -> Unit = { message ->
        if (message != null)
            errorMessage = message
    }
    val onFriendlistSucceed: (List<Friend>) -> Unit = { dataList ->
        friendListState.value = dataList
    }
    LaunchedEffect(Unit) {
        Backend.getFriendlist(context, onFriendlistSucceed, onFriendlistFailed)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Friendlist", fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(friendListState.value.size) { index ->
                FriendListItem(friend = friendListState.value[index])
            }
        }
    }
    Text(errorMessage, color = Color.Red)
}

@Composable
fun FriendListItem(friend: Friend) {
    Card(
        //elevation = CardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = friend.name, fontWeight = FontWeight.Bold)
                Text(text = friend.country)
            }
            if (friend.scooping) {
                Checkbox(
                    checked = true,
                    onCheckedChange = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FriendlistItemPreview() {
    FriendListItem(friend = Friend(name="Hans Meiser", true, "788323754", "Brasil"))
}


@Preview(
    showSystemUi = true,)
@Composable
fun FriendlistPreview() {
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("Home") }
    ModalNavigationDrawer(navController = navController, selectedItem){ Friendlist() }
}
