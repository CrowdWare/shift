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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.crowdware.shift.ui.widgets.NavigationDrawer
import at.crowdware.shift.R
import at.crowdware.shift.logic.Friend
import at.crowdware.shift.logic.getFriendsFromJSON
import at.crowdware.shift.ui.theme.Primary
import at.crowdware.shift.ui.widgets.NavigationItem
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.modifier.modifierLocalConsumer
import at.crowdware.shift.ui.widgets.NavigationManager

import lib.Lib.getUuid
import lib.Lib.getMatelist


@Composable
fun Friendlist() {
    var isMenuOpen by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(if (isMenuOpen) 45f else 0f)

    val friendListState = remember { mutableStateOf(emptyList<Friend>()) }
    var errorMessage by remember { mutableStateOf("") }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            stringResource(
                id = R.string.invite_message,
                stringResource(id = R.string.website_url), getUuid()
            )
        )
        type = "text/plain"
    }

    LaunchedEffect(Unit) {
        val json = getMatelist()
        val friendlist = getFriendsFromJSON(json)
        friendListState.value = friendlist
    }
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
                Text(errorMessage, color = Color.Red)
            } else {
                Text(stringResource(R.string.invite_advertise))
            }
            Spacer(modifier = Modifier.height(16.dp))
            /*
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                ),
                onClick = { context.startActivity(shareIntent) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.button_invite_friends),
                    style = TextStyle(fontSize = 20.sp)
                )
            }*/
        }
        FloatingActionButton(
            onClick = {
                isMenuOpen = !isMenuOpen
            },
            containerColor = Primary,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Friend",
                tint = Color.White,
                modifier = Modifier.rotate(rotationState)
            )
        }
        AnimatedVisibility(
            visible = isMenuOpen,
            modifier = Modifier
                .padding(vertical = 70.dp)
                .align(Alignment.BottomEnd)
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Text(text = stringResource(R.string.add_remote_friend), modifier = Modifier.padding(vertical = 16.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    FloatingActionButton(
                        onClick = { NavigationManager.navigate("add_remote_friend") },
                        containerColor = Primary,
                        modifier = Modifier.padding(bottom = 72.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = "Add remote friend",
                            tint = Color.White,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Text(text = stringResource(R.string.add_nearby_friend), modifier = Modifier.padding(vertical = 16.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    FloatingActionButton(
                        onClick = { NavigationManager.navigate("add_nearby_friend")},
                        containerColor = Primary,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PhotoCamera,
                            contentDescription = "Add nearby friend",
                            tint = Color.White,
                        )
                    }
                }
            }
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
                Text(text = friend.Name, fontWeight = FontWeight.Bold)
                Text(text = friend.Country)
                Text(friend.FriendsCount.toString() + stringResource(R.string.friends_invited))
            }
            if (friend.Scooping) {
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
    FriendListItem(friend = Friend("Hans Meiser", true, "788323754", "Brasil", 5))
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

    NavigationDrawer(list, selectedItem, "SHIFT"){ Friendlist() }
}