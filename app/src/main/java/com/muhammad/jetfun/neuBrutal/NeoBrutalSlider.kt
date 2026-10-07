package com.muhammad.jetfun.neuBrutal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun NeoBrutalSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    thumbRadiusDp: Dp = 16.dp,
    shadow: Dp = 4.dp,
    strokeWidth: Dp = 3.dp,
    trackHeight: Dp = 32.dp,
    trackColor: Color = Color(0xFFFFC2C7),
    fillColor: Color = Color(0xFFFF00FF),
    thumbColor: Color = Color(0xFFFFF6E9),
    dotRadiusDp: Dp = 3.dp,
    borderColor: Color = Color.Black,
    dotColor: Color = Color.Black,
) {
    val density = LocalDensity.current
    val currentOnChange by rememberUpdatedState(onValueChange)

    val span = valueRange.endInclusive - valueRange.start
    val fraction = if (span == 0f) 0f else ((value - valueRange.start) / span).coerceIn(0f, 1f)

    val thumbPx = with(density) { thumbRadiusDp.toPx() }
    val shadowPx = with(density) { shadow.toPx() }
    val dotRadiusPx = with(density) { dotRadiusDp.toPx() }
    val trackHeightPx = with(density) { trackHeight.toPx() }
    val strokeWidthPx = with(density) { strokeWidth.toPx() }

    val totalHeightDp = maxOf(
        trackHeight + shadow,
        (thumbRadiusDp * 2) + shadow
    ) + (strokeWidth * 2)

    val canvasModifier = modifier
        .fillMaxWidth()
        .height(totalHeightDp)
        .pointerInput(valueRange, steps) {
            detectTapGestures { offset ->
                currentOnChange(
                    xToValue(
                        x = offset.x,
                        width = size.width.toFloat(),
                        thumbR = thumbPx,
                        shadow = shadowPx,
                        range = valueRange,
                        steps = steps
                    )
                )
            }
        }
        .pointerInput(valueRange, steps) {
            detectDragGestures(
                onDragStart = { offset ->
                    currentOnChange(
                        xToValue(
                            x = offset.x,
                            width = size.width.toFloat(),
                            thumbR = thumbPx,
                            shadow = shadowPx,
                            range = valueRange,
                            steps = steps
                        )
                    )
                },
                onDrag = { change, _ ->
                    change.consume()
                    currentOnChange(
                        xToValue(
                            x = change.position.x,
                            width = size.width.toFloat(),
                            thumbR = thumbPx,
                            shadow = shadowPx,
                            range = valueRange,
                            steps = steps
                        )
                    )
                }
            )
        }

    Canvas(modifier = canvasModifier) {
        val trackWidth = size.width - shadowPx
        val trackTop = (size.height - shadowPx - trackHeightPx) / 2f
        val trackSize = Size(trackWidth, trackHeightPx)
        val centerY = trackTop + trackHeightPx / 2f

        val minX = thumbPx
        val maxX = max(minX, trackWidth - thumbPx)
        val thumbX = minX + fraction * (maxX - minX)

        val cornerRadius = CornerRadius(8.dp.toPx())

        drawRoundRect(
            color = borderColor,
            topLeft = Offset(x = shadowPx, y = trackTop + shadowPx),
            size = trackSize,
            cornerRadius = cornerRadius
        )

        drawRoundRect(
            color = trackColor,
            topLeft = Offset(x = 0f, y = trackTop),
            size = trackSize,
            cornerRadius = cornerRadius
        )

        val clip = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = trackTop,
                    right = trackWidth,
                    bottom = trackTop + trackHeightPx,
                    cornerRadius = cornerRadius
                )
            )
        }
        clipPath(clip) {
            drawRect(
                color = fillColor,
                topLeft = Offset(0f, trackTop),
                size = Size(width = thumbX, height = trackHeightPx)
            )
        }

        drawRoundRect(
            color = borderColor,
            topLeft = Offset(x = strokeWidthPx / 1.5f, y = trackTop + strokeWidthPx / 1.5f),
            size = Size(
                width = trackWidth - strokeWidthPx,
                height = trackHeightPx - strokeWidthPx
            ),
            cornerRadius = cornerRadius,
            style = Stroke(width = strokeWidthPx)
        )

        if (steps > 0) {
            for (i in 0..steps) {
                val stepX = minX + (i / steps.toFloat()) * (maxX - minX)
                drawCircle(
                    color = if (stepX <= thumbX + 0.5f) dotColor else dotColor.copy(alpha = 0.4f),
                    radius = dotRadiusPx,
                    center = Offset(stepX, centerY)
                )
            }
        }

        drawCircle(
            color = thumbColor,
            radius = thumbPx,
            center = Offset(thumbX, centerY)
        )

        drawCircle(
            color = borderColor,
            radius = thumbPx - strokeWidthPx / 2f,
            center = Offset(thumbX, centerY),
            style = Stroke(width = strokeWidthPx)
        )
    }
}

private fun xToValue(
    x: Float,
    width: Float,
    shadow: Float,
    thumbR: Float,
    range: ClosedFloatingPointRange<Float>,
    steps: Int,
): Float {
    val minX = thumbR
    val maxX = max(minX, width - shadow - thumbR)
    val spanX = maxX - minX

    var fraction = if (spanX > 0f) ((x - minX) / spanX).coerceIn(0f, 1f) else 0f

    if (steps > 0) {
        fraction = (fraction * steps).roundToInt() / steps.toFloat()
    }

    return range.start + fraction * (range.endInclusive - range.start)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFA52F)
@Composable
private fun NeoBrutalSliderStepsPreview() {
    var value1 by remember { mutableFloatStateOf(0.4f) }
    var value2 by remember { mutableFloatStateOf(0.4f) }
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        NeoBrutalSlider(
            value = value1,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { value1 = it }
        )
        NeoBrutalSlider(
            value = value2,
            steps = 10,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { value2 = it }
        )
    }
}