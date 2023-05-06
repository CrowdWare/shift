package at.crowdware.drawercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.crowdware.drawercompose.ui.theme.DrawerComposeTheme
import at.crowdware.drawercompose.ui.widgets.DrawerSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationView()
        }
    }
}

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("Home") }
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            ModalNavigationDrawer(navController, selectedItem){ MainPage()}
        }
        composable("Mate list") {
            ModalNavigationDrawer(navController, selectedItem){ MateList()}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNavigationDrawer(navController: NavController, selectedItem: MutableState<String>, content: @Composable() () -> Unit) {
    val openDialog = remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (openDialog.value) {
        About(
            openDialog = openDialog.value,
            onDismiss = { openDialog.value = false })
    }
    DrawerComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = { DrawerSheet(drawerState, navController, selectedItem) },
                content = {
                    Column() {
                        CenterAlignedTopAppBar(
                            title = { Text("SHIFT") },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Filled.Menu, contentDescription = null)
                                }
                            },
                            actions = {
                                IconButton(onClick = { openDialog.value = true }) {
                                    Icon(
                                        Icons.Filled.MoreVert,
                                        contentDescription = "About"
                                    )
                                }
                            }
                        )
                        content()
                    }
                }
            )
        }
    }
}

@Composable
fun About(openDialog: Boolean, onDismiss: () -> Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "About SHIFT") },
            text = {
                Text(
                    """
                        SHIFT CONNECTS US ALL
                        SHIFT will be decentral and will therefore not run on a single server.
                        Your SHIFT account is anonymous, only your real friends know who is behind your account.
                        No registration needed.
                        No server means, also no censorship and no ads.
                        SHIFT also creates a universal basic income and can be used to show gratitude to other members.
                        """.trimIndent()
                )
            },
            confirmButton = { TextButton(onClick = onDismiss ) { Text("OK") } },
            dismissButton = {}
        )
    }
}