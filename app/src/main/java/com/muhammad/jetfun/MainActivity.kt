package com.muhammad.jetfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import android.graphics.Color as AndroidColor
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muhammad.jetfun.neuBrutal.NeuBrutalCard
import com.muhammad.jetfun.neuBrutal.NeuBrutalCheckBox
import com.muhammad.jetfun.neuBrutal.NeuBrutalTextField
import com.muhammad.jetfun.neuBrutal.NeubrutalismButton
import com.muhammad.jetfun.ui.theme.JetFunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                AndroidColor.TRANSPARENT,
                AndroidColor.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                AndroidColor.TRANSPARENT,
                AndroidColor.TRANSPARENT
            )
        )
        setContent {
            JetFunTheme {
                var checkedState by remember { mutableStateOf(true) }
                var indeterminateState by remember { mutableStateOf(false) }
                val focusManager = LocalFocusManager.current
                val state = rememberTextFieldState()
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFF00FF))
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValues)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            24.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        NeuBrutalTextField(
                            state = state,
                            placeholder = R.string.password,
                            onKeyboardAction = {
                                focusManager.clearFocus()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_password),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }, trailingIcon = {
                                if (state.text.isNotEmpty()) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null,
                                                onClick = {
                                                    state.clearText()
                                                }
                                            )
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
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
                                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
                                )
                                Spacer(Modifier.height(24.dp))
                                NeubrutalismButton(
                                    text = "Add Note",
                                    onClick = {

                                    },
                                    modifier = Modifier.fillMaxWidth(0.8f),
                                    contentPadding = PaddingValues(vertical = 20.dp),
                                    textStyle = MaterialTheme.typography.titleMedium,
                                    backgroundColor = Color.Cyan
                                )
                            }
                        })
                    }
                }
            }
        }
    }
}