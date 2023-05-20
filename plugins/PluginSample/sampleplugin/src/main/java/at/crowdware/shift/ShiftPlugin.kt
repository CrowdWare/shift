package at.crowdware.shift

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface ShiftPlugin {

    fun menuTexts(): List<String>
    fun icons(): List<ImageVector>
    fun pages(): List<@Composable () -> Unit>
}