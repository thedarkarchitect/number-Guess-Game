package com.example.numberguesser

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GuessViewModel: ViewModel() {

    private val _state = MutableStateFlow(GuessingGameState())
    val state = _state.asStateFlow()

    fun updateTextField(userNo: String){
        _state.update { it.copy(userNumber = userNo) }
    }

    fun resetGame() {
        _state.value = GuessingGameState()//this resets the state with it's default values
    }

    fun onUserInput(userNumber: String, context: Context) {
        val userNumberInt = try {
            userNumber.toInt()//entered number turned to Int since it's a string
        }catch (e: Exception) {
            0
        }

        if(userNumberInt !in 1..99){
            Toast.makeText(
                context,
                "Please enter a number between 0 and 100.",
                Toast.LENGTH_SHORT
            ).show()
            return // we do this so that after the if test we can
        }

        val currentState = state.value //holds all values of the state

        val newGuessLeft = currentState.guessLeft - 1 // follows the guesses left
        val newGuessNoList = currentState.guessedNumbersList.plus(userNumberInt)//this add the entered value to the list as an int

        val newGameStage = when {
            userNumberInt == currentState.mysteryNumber -> GameStage.WON
            newGuessLeft == 0 -> GameStage.LOSE
            else -> GameStage.PLAYING
        }

        val newHintDescription = when {
            userNumberInt > currentState.mysteryNumber -> {
                "Hint\nYou are guessing bigger number than the mystery number."
            }
            userNumberInt < currentState.mysteryNumber -> {
                "Hint\nYou are guessing smaller number than the mystery number."
            }
            else -> ""
        }

        _state.update {
            it.copy(
                userNumber = "",//turns to a empty string after entering the current string in the textField
                guessLeft = newGuessLeft,
                guessedNumbersList = newGuessNoList,
                gameStage = newGameStage,
                hintDescription = newHintDescription
            )
        }
    }
}