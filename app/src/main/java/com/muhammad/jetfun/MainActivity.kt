package com.muhammad.jetfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.muhammad.jetfun.neuBrutalismAlertDialog.NeubrutalismAlertDialog
import com.muhammad.jetfun.ui.theme.JetFunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetFunTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFA52F))
                        .padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NeubrutalismAlertDialog(
                        showDialog = true,
                        onDismissRequest = {},
                        title = R.string.delete_notes,
                        message = R.string.delete_notes_desp,
                        confirmText = R.string.delete, dismissText = R.string.cancel,
                        onConfirm = {}, onDismiss = {}
                    )
                }
            }
        }
    }
}