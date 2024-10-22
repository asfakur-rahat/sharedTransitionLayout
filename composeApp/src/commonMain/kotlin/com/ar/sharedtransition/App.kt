package com.ar.sharedtransition

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ar.sharedtransition.navigation.SharedTransitionApp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        SharedTransitionApp()
    }
}
