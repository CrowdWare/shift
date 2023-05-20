package at.crowdware.shift.plugin

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import at.crowdware.shift.ShiftPlugin

class Plugin : ShiftPlugin {
    override fun menuTexts(): List<String> {
        return listOf("Sample Plugin 1", "Sample Plugin 2", "Sample Plugin 3", "Sample Plugin 4")
    }

    override fun pages(): List< @Composable () -> Unit> {
        return listOf( { Page1() }, { Page2() }, { Page3() }, { Page4() } )
    }

    override fun icons(): List<ImageVector> {
        return listOf(Icons.Default.Email, Icons.Default.Create, Icons.Default.AddCircle, Icons.Default.Call)
    }
}

@Composable
fun Page1() {
    Text("Hello from plugin page 1")
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