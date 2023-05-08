package at.crowdware.shift

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.crowdware.shift.ui.widgets.DrawerSheet
import kotlinx.coroutines.launch


@Composable
fun NavigationView() {
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("home") }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ModalNavigationDrawer(navController, selectedItem){ MainPage()}
        }
        composable("friendlist") {
            ModalNavigationDrawer(navController, selectedItem){ Friendlist()}
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
                                contentDescription = stringResource(R.string.navigation_about)
                            )
                        }
                    }
                )
                content()
            }
        }
    )
}

@Composable
fun About(openDialog: Boolean, onDismiss: () -> Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(R.string.about_shift)) },
            text = {
                Text(
                    stringResource(R.string.about_dialog_text)
                )
            },
            confirmButton = { TextButton(onClick = onDismiss ) { Text("OK") } },
            dismissButton = {}
        )
    }
}