package at.crowdware.drawercompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
fun MainPage() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text="MainPage")
    }
}

@Preview(
    showSystemUi = true,)
@Composable
fun MainPagePreview() {
    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("Home") }
    ModalNavigationDrawer(navController = navController, selectedItem){ MainPage()}
}
