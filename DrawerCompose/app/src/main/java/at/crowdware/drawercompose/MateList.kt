package at.crowdware.drawercompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
fun MateList() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text="Mate List")
    }
}

@Preview(
    showSystemUi = true,)
@Composable
fun MateListPreview() {
    val navController = rememberNavController()
    ModalNavigationDrawer(navController = navController){ MateList() }
}
