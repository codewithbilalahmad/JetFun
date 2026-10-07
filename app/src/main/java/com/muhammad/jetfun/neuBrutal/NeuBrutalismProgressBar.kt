package com.muhammad.jetfun.neuBrutal

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NeuBrutalismProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    shadowOffset: Dp = 4.dp,
    strokeWidth: Dp = 3.dp,
    cornerRadiusDp: Dp = 12.dp,
    trackHeight: Dp = 28.dp,
    shadowColor: Color = Color.Black,
    trackColor: Color = Color(0xFFFFC2C7),
    fillColor: Color = Color(0xFFFF00FF),
    borderColor: Color = Color.Black,
) {

    val totalHeight = trackHeight + shadowOffset

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(totalHeight)
    ) {
        val shadowOffsetPx = shadowOffset.toPx()
        val strokeWidthPx = strokeWidth.toPx()
        val cornerRadius = CornerRadius(cornerRadiusDp.toPx())

        val bodyWidth = size.width - shadowOffsetPx
        val bodyHeight = size.height - shadowOffsetPx

        drawRoundRect(
            color = shadowColor,
            topLeft = Offset(x = shadowOffsetPx, y = shadowOffsetPx),
            size = Size(width = bodyWidth, height = bodyHeight),
            cornerRadius = cornerRadius
        )

        drawRoundRect(
            color = trackColor,
            topLeft = Offset.Zero,
            size = Size(width = bodyWidth, height = bodyHeight),
            cornerRadius = cornerRadius
        )

        val fillWidth = bodyWidth * progress
        if (fillWidth > 0f) {
            clipRect(
                left = 0f,
                top = 0f,
                right = fillWidth,
                bottom = bodyHeight
            ) {
                drawRoundRect(
                    color = fillColor,
                    topLeft = Offset.Zero,
                    size = Size(width = bodyWidth, height = bodyHeight),
                    cornerRadius = cornerRadius
                )
                drawLine(
                    color = borderColor,
                    start = Offset(x = fillWidth, y = 0f),
                    end = Offset(x = fillWidth, y = bodyHeight),
                    cap = StrokeCap.Round,
                    strokeWidth = strokeWidthPx * 1.5f
                )
            }
        }

        drawRoundRect(
            color = borderColor,
            topLeft = Offset.Zero,
            size = Size(width = bodyWidth, height = bodyHeight),
            cornerRadius = cornerRadius,
            style = Stroke(width = strokeWidthPx)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFF00FF)
@Composable
private fun NeuBrutalismProgressBarPreview() {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF00FF))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        NeuBrutalismProgressBar(modifier = Modifier.fillMaxWidth(), progress = progress)
    }
}