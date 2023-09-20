package com.example.numberguesser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun GuessingGameScreen(
    modifier: Modifier = Modifier,
    viewModel: GuessViewModel
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    when(state.gameStage) {
        GameStage.PLAYING -> {
            ScreenContent(
                state = state,
                onValueChange = {
                viewModel.updateTextField(it)
            },
                onEnterButtonClicked =  {
                    viewModel.onUserInput(
                        userNumber = state.userNumber,
                        context = context
                    )
                }
            )
        }
        GameStage.WON -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {
                WinOrLoseDialog(
                    text = "Congratulations\nYou won",
                    buttonText = "Play Again",
                    mysteryNumber = state.mysteryNumber,
                    image = painterResource(id = R.drawable.ic_tropy),
                    resetGame = { viewModel.resetGame() }
                )
            }
        }
        GameStage.LOSE -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {
                WinOrLoseDialog(
                    text = "Better Luck next time",
                    buttonText = "Try Again",
                    mysteryNumber = state.mysteryNumber,
                    image = painterResource(id = R.drawable.ic_play_again),
                    resetGame = { viewModel.resetGame() }
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    state: GuessingGameState,
    onValueChange: (String) -> Unit,
    onEnterButtonClicked: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit){
        delay(500)
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(20.dp)
    ){
        Text(
            text = buildAnnotatedString {
                append("Guess left: ")
                withStyle(style = SpanStyle(color = Color.White)){
                    append("${state.guessLeft}")
                }
            },
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            state.guessedNumbersList.forEach { number ->
                Text(
                    text = "$number",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 42.sp,
                    modifier = modifier.padding(end = 20.dp)
                )
            }
        }

        Text(
            text = state.hintDescription,
            color = Color.White,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(40.dp))
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .focusRequester( focusRequester ),
            value = state.userNumber ,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 40.sp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onEnterButtonClicked() }
            )
        )
        Spacer(modifier = modifier.height(10.dp))
        Button(
            modifier = modifier
                .align(Alignment.End)
                .padding(end = 40.dp),
            onClick = onEnterButtonClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.Black
            )
        ){
            Text(text = "Enter", fontSize = 18.sp)
        }
    }
}

//@Preview
//@Composable
//fun PreviewScreen(){
//    GuessingGameScreen()
//}