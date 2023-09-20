package com.example.numberguesser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numberguesser.ui.theme.NumberGuesserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuesserTheme {
                GuessingGameScreen(viewModel = viewModel())
            }
        }
    }
}
