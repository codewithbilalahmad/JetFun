package com.muhammad.jetfun.neuBrutal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NeuBrutalCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    borderWidth: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    checkedColor: Color = Color(0xFFFF00FF),
    unCheckedColor: Color = Color(0xFFFFC2C7),
    checkMarkColor: Color = Color.Black,
    shadowColor: Color = Color.Black,
    shadowOffset: Dp = 3.dp,
    enabled: Boolean = true,
    isIndeterminate: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressTranslationOffset by animateFloatAsState(
        targetValue = if (isPressed && enabled) shadowOffset.value * 0.65f else 0f,
        animationSpec = spring(
            stiffness = Spring.StiffnessHigh,
            dampingRatio = Spring.DampingRatioLowBouncy
        ),
        label = "pressTranslationOffset"
    )

    val containerColor by animateColorAsState(
        targetValue = if (checked || isIndeterminate) checkedColor else unCheckedColor,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "containerColor"
    )

    val checkProgress by animateFloatAsState(
        targetValue = if (checked && !isIndeterminate) 1f else 0f,
        animationSpec = spring(
            stiffness = Spring.StiffnessMedium,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "checkProgress"
    )

    val indeterminateProgress by animateFloatAsState(
        targetValue = if (isIndeterminate) 1f else 0f,
        animationSpec = spring(
            stiffness = Spring.StiffnessMedium,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "indeterminateProgress"
    )

    val checkPath = remember { Path() }
    val pathMeasure = remember { PathMeasure() }
    val animatedPath = remember { Path() }

    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(size)
            .semantics {
                stateDescription = when {
                    isIndeterminate -> "Indeterminate"
                    checked -> "Checked"
                    else -> "Unchecked"
                }
            }
            .drawBehind {
                val fullShadowPx = shadowOffset.toPx()
                val currentOffsetPx = pressTranslationOffset.dp.toPx()

                val remainingShadowX = fullShadowPx - currentOffsetPx
                val remainingShadowY = fullShadowPx - currentOffsetPx

                val cornerRadiusPx = if (shape is RoundedCornerShape) {
                    shape.topStart.toPx(this.size, this)
                } else {
                    0f
                }

                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(currentOffsetPx + remainingShadowX, currentOffsetPx + remainingShadowY),
                    size = Size(this.size.width, this.size.height),
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )
            }
            .graphicsLayer {
                translationX = pressTranslationOffset.dp.toPx()
                translationY = pressTranslationOffset.dp.toPx()
            }
            .clip(shape)
            .background(color = containerColor)
            .border(width = borderWidth, color = shadowColor, shape = shape)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.55f)) {
            val canvasWidth = this.size.width
            val canvasHeight = this.size.height
            val strokeWidthPx = borderWidth.toPx() * 1.4f

            if (indeterminateProgress > 0f) {
                val startX = canvasWidth * 0.15f
                val endX = canvasWidth * 0.85f
                val currentEndX = startX + (endX - startX) * indeterminateProgress
                val y = canvasHeight * 0.5f

                drawLine(
                    color = checkMarkColor,
                    start = Offset(startX, y),
                    end = Offset(currentEndX, y),
                    strokeWidth = strokeWidthPx,
                    cap = StrokeCap.Round
                )
            }

            if (checkProgress > 0f) {
                checkPath.reset()
                checkPath.moveTo(canvasWidth * 0.15f, canvasHeight * 0.5f)
                checkPath.lineTo(canvasWidth * 0.42f, canvasHeight * 0.78f)
                checkPath.lineTo(canvasWidth * 0.85f, canvasHeight * 0.22f)

                pathMeasure.setPath(checkPath, false)
                animatedPath.reset()
                pathMeasure.getSegment(
                    startDistance = 0f,
                    stopDistance = pathMeasure.length * checkProgress,
                    destination = animatedPath
                )

                drawPath(
                    path = animatedPath,
                    color = checkMarkColor,
                    style = Stroke(
                        width = strokeWidthPx,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
private fun NeuBrutalCheckBoxPreview() {
    var checkedState by remember { mutableStateOf(true) }
    var indeterminateState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        NeuBrutalCheckBox(
            checked = checkedState,
            isIndeterminate = indeterminateState,
            onCheckedChange = {
                if (indeterminateState) {
                    indeterminateState = false
                    checkedState = true
                } else {
                    checkedState = !checkedState
                }
            }
        )
    }
}