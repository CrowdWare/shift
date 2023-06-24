package at.crowdware.shift.plugin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import at.crowdware.shift.plugin.ui.pages.Chat
import at.crowdware.shiftapi.ShiftPlugin



class Plugin : ShiftPlugin {
    override fun getName(): String {
        return "Sample Plugin"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun menuTexts(): List<String> {
        return listOf("Chat", "Sample Plugin 2", "Sample Plugin 3", "Sample Plugin 4")
    }

    override fun pages(): List< @Composable () -> Unit> {
        return listOf( { Chat() }, { Page2() }, { Page3() }, { Page4() } )
    }

    override fun icons(): List<ImageVector> {
        return listOf(Icons.Default.Email, Icons.Default.Create, Icons.Default.AddCircle, Icons.Default.Call)
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


