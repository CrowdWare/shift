package at.crowdware.shift

import android.app.Activity
import android.app.LauncherActivity
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.crowdware.shift.logic.LocaleManager
import at.crowdware.shift.ui.widgets.DropDownListbox
import at.crowdware.shift.ui.widgets.rememberDropDownListboxStateHolder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
    val scope = rememberCoroutineScope()
    //region vars for the DropDownlistbox
    val context = LocalContext.current
    val languages = LocaleManager.getLanguages()
    val index = LocaleManager.getLanguageIndex()
    val currentActivity = LocalContext.current as? Activity
    val onSelectedIndexChanged: (Int) -> Unit = { idx ->
        LocaleManager.setLocale(context, idx)
        currentActivity?.recreate()
    }
    val stateHolderLanguage =
        rememberDropDownListboxStateHolder(languages, index, onSelectedIndexChanged)
    //endregion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.settings), fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(4.dp))
        DropDownListbox(
            label = stringResource(R.string.select_preferred_language),
            stateHolder = stateHolderLanguage,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Plugins", fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                //.background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            items(items) { item ->
                var deleted by remember { mutableStateOf(false) }
                var unread by remember { mutableStateOf(true) }
                /*val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) unread = !unread

                        if (it == DismissValue.DismissedToStart) {
                            deleted = true
                            return@rememberDismissState true
                        }
                        return@rememberDismissState false
                    }
                )*/
                /*
                if (deleted) {
                    scope.launch {
                        delay(500)

                        //onDelete(item)
                    }
                }*/
                AnimatedVisibility(
                    visible = !deleted,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    val dismissState = rememberDismissState()
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val direction =
                                dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.LightGray
                                    DismissValue.DismissedToEnd -> Color.Green
                                    DismissValue.DismissedToStart -> Color.Red
                                }
                            )
                            val alignment = when (direction) {
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                            }
                            val icon = when (direction) {
                                DismissDirection.StartToEnd -> Icons.Default.Done
                                DismissDirection.EndToStart -> Icons.Default.Delete
                            }
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        },
                        dismissContent = {
                            Card (
                                /*elevation = animateDpAsState(if (dismissState.dismissDirection != null) {4.dp} else {0.dp}).value*/
                            ) {
                                ListItem(
                                    headlineContent = {
                                        Text(item, fontWeight = if (unread) FontWeight.Bold else null,
                                            textDecoration = if (unread) TextDecoration.None else TextDecoration.LineThrough,)
                                    },
                                    supportingContent = { Text("Swipe me left or right!") }
                                )
                                Divider()
                            }
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = { /*TODO*/ }) {
            Text("Install plugin")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview() {
    Settings()
}