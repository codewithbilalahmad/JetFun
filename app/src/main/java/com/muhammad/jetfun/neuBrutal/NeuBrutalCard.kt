package com.muhammad.jetfun.neuBrutal

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NeuBrutalCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = Color(0xFFFFE57F),
    borderWidth: Dp = 2.dp,
    shadowColor: Color = Color.Black,
    shadowOffset: Dp = 12.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit,
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
    Card(
        modifier = modifier
            .drawBehind {
                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(shadowOffset, shadowOffset),
                    size = Size(size.width, size.height),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            .then(
                if (onClick != null) Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null, onClick = onClick
                ) else Modifier
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(width = borderWidth, color = shadowColor)
    ) {
        content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFA52F)
@Composable
private fun NeuBrutalCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp), contentAlignment = Alignment.Center
    ) {
        NeuBrutalCard(modifier = Modifier.fillMaxWidth(), onClick = {}, content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Notes yet",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "You don’t have any notes yet. Start by creating your first note to keep your thoughts, ideas, and important information organized and easy to find.",
                    style = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.Center)
                )
                Spacer(Modifier.height(24.dp))
                NeubrutalismButton(
                    text = "Add Note",
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    backgroundColor = Color.Cyan
                )
            }
        })
    }
}