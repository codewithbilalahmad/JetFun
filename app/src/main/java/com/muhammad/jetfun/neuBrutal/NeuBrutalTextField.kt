package com.muhammad.jetfun.neuBrutal

import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muhammad.jetfun.R
import com.muhammad.jetfun.ui.theme.JetFunTheme

@Composable
fun NeuBrutalTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    @StringRes placeholder: Int,
    leadingIcon : (@Composable () -> Unit)?=null,
    trailingIcon : (@Composable () -> Unit)?=null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    borderColor: Color = Color.Black,
    onKeyboardAction : () -> Unit = {},
    borderWidth: Dp = 3.dp,
    shadowOffset: Dp = 3.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var isFocused by remember { mutableStateOf(false) }
    val activeShadowOffset by animateDpAsState(
        targetValue = if (isFocused) shadowOffset + 2.dp else shadowOffset,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "textFieldShadow"
    )
    Box(
        modifier = modifier
            .neuBrutalShadow(
                shadowColor = borderColor,
                offsetX = activeShadowOffset,
                offsetY = activeShadowOffset,
                shape = shape
            )
    ) {
        BasicTextField(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .border(borderWidth, borderColor, shape)
                .background(backgroundColor, shape)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            textStyle = MaterialTheme.typography.titleSmall,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.surface),
            keyboardOptions = keyboardOptions,
            onKeyboardAction ={
                onKeyboardAction()
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if(leadingIcon != null){
                        leadingIcon()
                        Spacer(Modifier.width(8.dp))
                    }
                    Box(Modifier.weight(1f)) {
                        if (state.text.isEmpty()) {
                            Text(
                                text = stringResource(placeholder),
                                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.surface),
                            )
                        }
                        innerTextField()
                    }
                    if(trailingIcon != null){
                        Spacer(Modifier.width(8.dp))
                        trailingIcon()
                    }
                }
            }
        )
    }
}

fun Modifier.neuBrutalShadow(
    shadowColor: Color = Color.Black,
    offsetX: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    shape: Shape,
): Modifier = this
    .drawBehind {
        val xPx = offsetX.toPx()
        val yPx = offsetY.toPx()
        val outline = shape.createOutline(size, layoutDirection, this)
        val path = Path().apply {
            addOutline(outline)
            translate(Offset(x = xPx, y = yPx))
        }
        drawPath(path = path, color = shadowColor)
    }

@Preview(showBackground = true, backgroundColor = 0xFFFF00FF)
@Composable
private fun NeuBrutalTextFieldPreview() {
    JetFunTheme(darkTheme = false) {
        val state = rememberTextFieldState()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            NeuBrutalTextField(
                state = state,
                placeholder = R.string.password,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}