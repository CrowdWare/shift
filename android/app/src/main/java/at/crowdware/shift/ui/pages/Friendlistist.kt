/****************************************************************************
 * Copyright (C) 2023 CrowdWare
 *
 * This file is part of SHIFT.
 *
 *  SHIFT is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SHIFT is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************/
package at.crowdware.shift.ui.pages

import android.content.Intent
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.ui.widgets.NavigationDrawer
import at.crowdware.shift.R
import at.crowdware.shift.logic.Backend
import at.crowdware.shift.logic.Friend
import at.crowdware.shift.ui.theme.OnPrimary
import at.crowdware.shift.ui.theme.Primary
import at.crowdware.shift.ui.widgets.NavigationItem
import java.time.LocalDate

@Composable
fun Friendlist() {
    val context = LocalContext.current
    val friendListState = remember { mutableStateOf(emptyList<Friend>()) }
    var errorMessage by remember { mutableStateOf("") }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            stringResource(id = R.string.invite_message, stringResource(id = R.string.website_url), ""/*Backend.getAccount().uuid*/)
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val onFriendlistFailed: (String?) -> Unit = { message ->
        if (message != null)
            errorMessage = message
    }
    val onFriendlistSucceed: (List<Friend>) -> Unit = { dataList ->
        friendListState.value = dataList
    }

    LaunchedEffect(Unit) {
        //Backend.getFriendlist(context, onFriendlistSucceed, onFriendlistFailed)
    }

    // rTNV7cTZ8kWU6JwUohKGIA==
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.friendlist), fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(4.dp))
        if(friendListState.value.size > 0) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(friendListState.value.size) { index ->
                    FriendListItem(friend = friendListState.value[index])
                }
            }
            Text(errorMessage, color = Color.Red)
        } else {
            Text(stringResource(R.string.invite_advertise))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            ),
            onClick = { context.startActivity(shareIntent) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.button_invite_friends), style = TextStyle(fontSize = 20.sp))
        }
    }
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
                Text(friend.friends_count.toString() + stringResource(R.string.friends_invited))
            }
            if (friend.scooping) {
                Text(stringResource(R.string.is_scooping), modifier = Modifier.padding(16.dp))
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
    FriendListItem(friend = Friend(name="Hans Meiser", true, "788323754", "Brasil", 5))
}


@Preview(
    showSystemUi = true,)
@Composable
fun FriendlistPreview() {
    val selectedItem = remember { mutableStateOf("Home") }
    val list = mutableListOf(
        NavigationItem("home", Icons.Default.Home, stringResource(R.string.navigation_home)),
        NavigationItem("friendlist", Icons.Default.Face, stringResource(R.string.navigation_friendlist))
    )

    NavigationDrawer(list, selectedItem){ Friendlist() }
}