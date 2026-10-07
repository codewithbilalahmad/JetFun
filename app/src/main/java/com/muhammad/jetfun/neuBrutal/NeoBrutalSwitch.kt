package com.muhammad.jetfun.neuBrutal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NeoBrutalSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    borderColor: Color = Color.Black,
    thumbColor: Color = Color(0xFFFFC2C7),
    shadowColor: Color = Color.Black,
    shadowOffset: Dp = 3.dp,
    borderWidth: Dp = 2.dp,
    activeColor: Color = Color(0xFFFF00FF),
    unActiveColor: Color = Color(0xFFFFC2C7),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val containerColor by animateColorAsState(
        targetValue = if (checked) activeColor else unActiveColor,
        animationSpec = tween(250, easing = LinearOutSlowInEasing),
        label = "containerColor"
    )
    val thumbProgress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
        label = "thumbPosition"
    )
    val switchWidth = 60.dp
    val switchHeight = 36.dp
    Canvas(
        modifier = modifier
            .padding(bottom = shadowOffset, end = shadowOffset)
            .size(width = switchWidth, height = switchHeight)
            .toggleable(
                value = checked,
                onValueChange = onCheckChange,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        val cornerRadiusPx = size.height  / 2f
        val shadowOffsetPx = shadowOffset.toPx()
        val padding = 6.dp.toPx()
        val strokeWidthPx = borderWidth.toPx()
        val thumbRadiusPx = (size.height - (padding * 2)) / 2f

        val startX = padding + thumbRadiusPx
        val endX = size.width - padding - thumbRadiusPx
        val animatedThumbX = startX + (endX - startX) * thumbProgress

        drawRoundRect(
            color = shadowColor,
            size = Size(width = size.width, height = size.height),
            cornerRadius = CornerRadius(cornerRadiusPx),
            topLeft = Offset(x = shadowOffsetPx, y = shadowOffsetPx)
        )

        drawRoundRect(
            color = containerColor,
            size = Size(width = size.width, height = size.height),
            cornerRadius = CornerRadius(cornerRadiusPx)
        )
        drawRoundRect(
            color = borderColor,
            topLeft = Offset.Zero,
            size = Size(width = size.width, height = size.height),
            cornerRadius = CornerRadius(cornerRadiusPx),
            style = Stroke(width = strokeWidthPx)
        )
        drawCircle(
            color = thumbColor,
            radius = thumbRadiusPx,
            center = Offset(x = animatedThumbX, y = size.height / 2f)
        )

        drawCircle(
            color = borderColor,
            radius = thumbRadiusPx,
            center = Offset(x = animatedThumbX, y = size.height / 2f),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFF00FF)
@Composable
private fun NeoBrutalProgressBarAndSwitchPreview() {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )
    var checked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NeoBrutalSwitch(checked = checked, onCheckChange = { check ->
            checked = check
        })
        NeuBrutalismProgressBar(modifier = Modifier.fillMaxWidth(), progress = progress)
    }
}