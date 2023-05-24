/*
Based on this repo:
https://github.com/MatthiasKerat/CircularProgressIndicatorYT
 */

package at.crowdware.shift.ui.widgets

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*
import at.crowdware.shift.ui.theme.*


@Composable
fun CircularValuePicker(
    modifier: Modifier = Modifier,
    initialValue:Int,
    primaryColor: Color = Primary,
    secondaryColor:Color = Tertiary,
    textColor:Color = OnTertiary,
    minValue:Int = 0,
    maxValue:Int = 100,
    stepSize: Int = 1,
    unit: String = "%",
    onPositionChange:(Int)->Unit
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }

    var changeAngle by remember {
        mutableStateOf(0f)
    }

    var dragStartedAngle by remember {
        mutableStateOf(0f)
    }

    var oldPositionValue by remember {
        mutableStateOf(initialValue)
    }

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            dragStartedAngle = atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat()
                            dragStartedAngle = (360f - dragStartedAngle).mod(360f) // Subtract the angle from 360
                        },
                        onDrag = { change, _ ->
                            var touchAngle = atan2(
                                x = circleCenter.y - change.position.y,
                                y = circleCenter.x - change.position.x
                            ) * (180f / PI).toFloat()
                            touchAngle = (360f - touchAngle).mod(360f) // Subtract the angle from 360

                            val currentAngle = oldPositionValue * 360f / (maxValue - minValue)
                            changeAngle = touchAngle - currentAngle

                            val lowerThreshold = currentAngle - (360f / (maxValue - minValue) * 5)
                            val higherThreshold = currentAngle + (360f / (maxValue - minValue) * 5)

                            if (dragStartedAngle in lowerThreshold..higherThreshold) {
                                positionValue =
                                    (oldPositionValue + (changeAngle / (360f / (maxValue - minValue))).roundToInt())
                            }
                        },
                        onDragEnd = {
                            oldPositionValue = positionValue
                            onPositionChange(positionValue)
                        }
                    )
                }
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            // the 80f was just try and error, should be calculated from line length
            val circleRadius = (width - circleThickness - 80f) / 2
            circleCenter = Offset(x = width / 2f, y = height / 2f)


            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )


            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )

            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = (360f / maxValue) * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f) / 2f,
                    (height - circleRadius * 2f) / 2f
                )

            )

            val handleRadius = circleThickness // radius of the handle
            val handleAngleDegrees = (positionValue * 360f / (maxValue - minValue) - 90f) // Subtract 90 from the angle
            val handleAngleRad = handleAngleDegrees * PI / 180f

            val handlePosition = Offset(
                x = (circleRadius * cos(handleAngleRad) + circleCenter.x).toFloat(),
                y = (circleRadius * sin(handleAngleRad) + circleCenter.y).toFloat()
            )

            // draw the handle
            drawCircle(
                color = primaryColor,
                radius = handleRadius,
                center = handlePosition
            )

            val outerRadius = circleRadius + circleThickness / 2f
            val gap = 15f
            for (i in 0..(maxValue - minValue)) {
                val color =
                    if (i < positionValue - minValue) primaryColor else primaryColor.copy(alpha = 0.3f)
                val angleInDegrees = i * 360f / (maxValue - minValue).toFloat()
                val angleInRad = angleInDegrees * PI / 180f + PI / 2f

                val yGapAdjustment = cos(angleInDegrees * PI / 180f) * gap
                val xGapAdjustment = -sin(angleInDegrees * PI / 180f) * gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val end = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleThickness + circleCenter.y + yGapAdjustment).toFloat()
                )

                rotate(
                    angleInDegrees,
                    pivot = start
                ) {
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = 1.dp.toPx()
                    )
                }

            }

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "${positionValue * stepSize} $unit",
                        circleCenter.x,
                        circleCenter.y + 45.dp.toPx() / 3f,
                        Paint().apply {
                            textSize = 38.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = textColor.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CircularValuePicker(
        modifier = Modifier
            .size(250.dp)
        ,
        initialValue = 25,
        onPositionChange = {}
    )
}
