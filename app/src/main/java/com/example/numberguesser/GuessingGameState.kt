package com.example.numberguesser

data class GuessingGameState(
    val userNumber: String = "",
    val guessLeft: Int = 5,
    val guessedNumbersList: List<Int> = emptyList(),
    //the random number
    val mysteryNumber: Int = (1..99).random(),
    val hintDescription: String = "Guess\nthe mystery number between\n0 and 100",
    val gameStage: GameStage = GameStage.PLAYING
)

enum class GameStage {
    WON,
    LOSE,
    PLAYING
}