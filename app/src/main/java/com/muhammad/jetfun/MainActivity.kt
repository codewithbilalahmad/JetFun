package com.muhammad.jetfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.muhammad.jetfun.neuBrutalismProgressBar.NeuBrutalismProgressBar
import com.muhammad.jetfun.ui.theme.JetFunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetFunTheme {
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
        }
    }
}