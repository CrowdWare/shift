package at.crowdware.shift.plugin.ui.widgets

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun RoundInitialImage(
    name: String,
    size: Dp,
    backgroundColor: Color,
    textColor: Int,
    textSize: TextUnit
) {
    Canvas(
        modifier = Modifier.size(size)
    ) {
        val centerX = size.toPx() / 2
        val centerY = size.toPx() / 2

        // Draw background circle
        drawCircle(
            color = backgroundColor
        )

        // Draw initial text
        drawInitialText(
            text = extractInitials(name),
            x = centerX,
            y = centerY,
            textColor = textColor,
            textSize = textSize
        )
    }
}

private fun DrawScope.drawInitialText(
    text: String,
    x: Float,
    y: Float,
    textColor: Int,
    textSize: TextUnit
) {
    val paint = Paint()
    paint.textAlign = Paint.Align.CENTER
    paint.textSize = textSize.value
    paint.color = textColor

    drawIntoCanvas { canvas ->
        canvas.nativeCanvas.drawText(
            text,
            x,
            y +(paint.fontMetrics.descent - paint.fontMetrics.ascent) / 2 - paint.fontMetrics.descent,
            paint
        )
    }
}


private fun extractInitials(name: String): String {
    val words = name.split(" ")
    return if (words.size >= 2) {
        val firstInitial = words.firstOrNull()?.take(1)
        val lastInitial = words.lastOrNull()?.take(1)
        "$firstInitial$lastInitial"
    } else {
        name.take(1)
    }
}