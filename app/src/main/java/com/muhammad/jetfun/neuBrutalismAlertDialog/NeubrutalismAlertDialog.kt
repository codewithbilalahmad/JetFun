package com.muhammad.jetfun.neuBrutalismAlertDialog

import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.muhammad.jetfun.R
import com.muhammad.jetfun.ui.theme.BorderAndShadowColor
import com.muhammad.jetfun.ui.theme.CancelButtonColor
import com.muhammad.jetfun.ui.theme.DeleteButtonColor
import com.muhammad.jetfun.ui.theme.DialogBodyColor
import com.muhammad.jetfun.ui.theme.DialogTitleBarColor
import com.muhammad.jetfun.ui.theme.JetFunTheme
import com.muhammad.jetfun.ui.theme.WindowDotBlue
import com.muhammad.jetfun.ui.theme.WindowDotRed
import com.muhammad.jetfun.ui.theme.WindowDotYellow

@Composable
fun NeubrutalismAlertDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    shadowOffset: Dp = 6.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes confirmText: Int,
    @StringRes dismissText: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .drawBehind {
                        val shadowPx = shadowOffset.toPx()
                        drawRoundRect(
                            color = BorderAndShadowColor,
                            topLeft = Offset(shadowPx, shadowPx),
                            size = Size(width = size.width, height = size.height),
                            cornerRadius = CornerRadius(16.dp.toPx())
                        )
                    }
                    .clip(shape)
                    .border(width = 2.5.dp, BorderAndShadowColor, shape)
                    .background(DialogBodyColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DialogTitleBarColor)
                            .border(
                                width = 2.dp,
                                color = BorderAndShadowColor
                            )
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WindowDot(color = WindowDotRed)
                            WindowDot(color = WindowDotYellow)
                            WindowDot(color = WindowDotBlue)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = stringResource(title),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(message),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NeubrutalismButton(
                                text = stringResource(dismissText),
                                backgroundColor = CancelButtonColor,
                                onClick = onDismiss
                            )
                            NeubrutalismButton(
                                text = stringResource(confirmText),
                                backgroundColor = DeleteButtonColor,
                                onClick = onConfirm
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WindowDot(color: Color) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .background(color, CircleShape)
            .border(1.5.dp, BorderAndShadowColor, CircleShape)
    )
}

@Composable
fun NeubrutalismButton(
    modifier: Modifier = Modifier,
    text: String,
    shadowOffset: Dp = 10.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val shadowOffset by animateFloatAsState(
        targetValue = if (isPressed) shadowOffset.value / 2f else shadowOffset.value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "shadowOffset"
    )
    Box(
        modifier = modifier
            .drawBehind {
                drawRoundRect(
                    color = BorderAndShadowColor,
                    topLeft = Offset(shadowOffset, shadowOffset),
                    size = Size(size.width, size.height),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            .clip(shape)
            .border(2.dp, BorderAndShadowColor, shape)
            .background(backgroundColor)
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFA52F)
@Composable
private fun NeubrutalismAlertDialogPreview() {
    JetFunTheme(darkTheme = false) {
        var showDialog by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NeubrutalismAlertDialog(
                showDialog = showDialog,
                onDismissRequest = { showDialog = false },
                title = R.string.delete_notes,
                message = R.string.delete_notes_desp,
                confirmText = R.string.delete, dismissText = R.string.cancel,
                onConfirm = {
                }, onDismiss = {
                }
            )
        }
    }
}