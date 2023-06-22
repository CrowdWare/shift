package at.crowdware.shift.plugin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.crowdware.shiftapi.Friend
import at.crowdware.shiftapi.FriendApi
import at.crowdware.shiftapi.ShiftPlugin

import at.crowdware.shiftapi.sendPeerMessage

class Plugin : ShiftPlugin {
    override fun getName(): String {
        return "Sample Plugin"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun menuTexts(): List<String> {
        return listOf("Sample Plugin 1", "Sample Plugin 2", "Sample Plugin 3", "Sample Plugin 4")
    }

    override fun pages(): List< @Composable () -> Unit> {
        return listOf( { Page1() }, { Page2() }, { Page3() }, { Page4() } )
    }

    override fun icons(): List<ImageVector> {
        return listOf(Icons.Default.Email, Icons.Default.Create, Icons.Default.AddCircle, Icons.Default.Call)
    }
}

@Composable
fun Page1() {
    //sendPeerMessage("", "")
    val friendListState = remember { mutableStateOf(emptyList<Friend>()) }

    LaunchedEffect(Unit) {
        val friendlist = FriendApi.getFriendList()
        friendListState.value = friendlist
    }
    Text("Friendlist")
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            if (friendListState.value.isNotEmpty()) {
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
        }
    }
}

@Composable
fun Page2() {
    Text("Hello from plugin page 2")
}

@Composable
fun Page3() {
    Text("Hello from plugin page 3")
}

@Composable
fun Page4() {
    Text("Hello from plugin page 4")
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
                .padding(horizontal = 16.dp, vertical = 8.dp).clickable { sendPeerMessage(friend.Uuid, "Hello")  },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = friend.Name, fontWeight = FontWeight.Bold)
                Text(text = friend.Country)
                Text(friend.FriendsCount.toString() + " friends invited")
            }
            if (friend.Scooping) {
                Text("is scooping", modifier = Modifier.padding(16.dp))
                Checkbox(
                    checked = true,
                    onCheckedChange = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}