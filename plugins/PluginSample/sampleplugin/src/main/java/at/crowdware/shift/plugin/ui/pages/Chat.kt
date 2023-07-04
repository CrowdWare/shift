package at.crowdware.shift.plugin.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.plugin.logic.Message
import at.crowdware.shift.plugin.logic.MessageManager
import at.crowdware.shift.plugin.ui.widgets.RoundInitialImage
import at.crowdware.shiftapi.FriendApi
import at.crowdware.shiftapi.sendPeerMessage
import at.crowdware.shiftapi.ui.theme.OnPrimary
import at.crowdware.shiftapi.ui.theme.Primary
import at.crowdware.shiftapi.ui.theme.Secondary
import kotlinx.coroutines.delay


@Composable
fun Chat() {
    val friends = FriendApi.getFriendList()
    MessageManager.initialize(context = LocalContext.current)
    val messageListState = remember { mutableStateOf(MessageManager.getPeerMessages()) }

    LaunchedEffect(true) {
        while (true) {
            val list = MessageManager.getPeerMessages()
            messageListState.value = list
            delay(3000L)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (messageListState.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(messageListState.value.size) { index ->
                        MessageListItem(msg = messageListState.value[index])
                    }
                }
            } else {
                Text("There are no massages yet. Start clicking the [+] button to send a message.")
            }
        }
        FloatingActionButton(
            onClick = {
                for (friend in friends) {
                    println("Friend: ${friend.Name} ${friend.Uuid}")
                    if(friend.HasPeerDat) {
                        println("Sending a message to ${friend.Name} ${friend.Uuid}")
                        sendPeerMessage(friend.Uuid, "Hello, greetings from me.")
                    }
                }
            },
            containerColor = Primary,
            contentColor = OnPrimary,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Friend",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun MessageListItem(msg: Message) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundInitialImage(
                name = msg.Name,
                size = 60.dp,
                backgroundColor = Secondary,
                textColor = Color.White.toArgb(),
                textSize = 60.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = msg.Name, fontWeight = FontWeight.Bold)
                Text(text = "${msg.Message}", maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(300.dp))
            }
            Column() {
                Text(text=msg.Time)
                Text(text = "")
            }
        }
    }
}


@Preview
@Composable
fun MessageListItemPreview() {
    MessageListItem(msg = Message("","Hans Meiser", "Hallo wie geht es Dir? Diese Nachricht ist etwas länger.",  "1234",  "12:56"))
}