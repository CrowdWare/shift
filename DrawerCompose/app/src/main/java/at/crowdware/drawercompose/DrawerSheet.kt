package at.crowdware.drawercompose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.crowdware.drawercompose.ui.theme.DrawerComposeTheme
import kotlinx.coroutines.launch

data class MenuItem(val icon: ImageVector, val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerSheet(drawerState: DrawerState, navController: NavController){
    val items = listOf(
        MenuItem(Icons.Default.Home, "Home"),
        MenuItem(Icons.Default.Face, "Mate list"))
    val selectedItem = remember { mutableStateOf(items[0]) }
    val scope = rememberCoroutineScope()
    ModalDrawerSheet(modifier = Modifier.width((LocalConfiguration.current.screenWidthDp * 0.8).dp)) {
        Spacer(Modifier.height(12.dp))
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = item.text) },
                label = { Text(item.text) },
                selected = item == selectedItem.value,
                onClick = {
                    scope.launch { drawerState.close() }
                    selectedItem.value = item
                    navController.navigate(item.text)
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun DrawerPreview() {
    val navController = rememberNavController()
    DrawerComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            DrawerSheet(drawerState = rememberDrawerState(DrawerValue.Closed), navController)
        }
    }
}
