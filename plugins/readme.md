# Plugins
One essential feature of Shift the ability to install plugins.  
That makes it easy for other developers to add usefull content.  
And the benefit for the user is the possibility to decide which functionality to install.  

A great benefit for the project is that the core app doesn't need to be updated that often.

# Technology
All plugins will be coded using Kotlin and Jetpack Compose.  
That makes it very easy to build smart plugins like in this sample.

# Sample
```kotlin
package at.crowdware.shift.plugin

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import at.crowdware.shift.ShiftPlugin

class Plugin : ShiftPlugin {
    override fun getName(): String {
        return "Sample Plugin"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun menuTexts(): List<String> {
        return listOf("Sample Plugin")
    }

    override fun pages(): List< @Composable () -> Unit> {
        return listOf( { Page1() })
    }

    override fun icons(): List<ImageVector> {
        return listOf(Icons.Default.Email)
    }
}

@Composable
fun Page1() {
    Text("Hello World, from a plugin")
}
```

