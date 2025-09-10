package edu.ucne.registrojugador.presentation.juego

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeScreen(onBack: () -> Unit) {
    var board by remember { mutableStateOf(List(3) { MutableList(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }

    fun resetGame() {
        board = List(3) { MutableList(3) { "" } }
        currentPlayer = "X"
        winner = null
    }

    fun checkWinner(): String? {
        // Filas y columnas
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != "")
                return board[i][0]
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != "")
                return board[0][i]
        }
        // Diagonales
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != "")
            return board[0][0]
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != "")
            return board[0][2]
        return null
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tic Tac Toe", fontSize = 32.sp, modifier = Modifier.padding(bottom = 16.dp))

        board.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .border(1.dp, Color.Black)
                            .clickable(enabled = winner == null && cell == "") {
                                board = board.mapIndexed { r, row ->
                                    row.mapIndexed { c, value ->
                                        if (r == rowIndex && c == colIndex) currentPlayer else value
                                    }.toMutableList()
                                }
                                winner = checkWinner()
                                if (winner == null) currentPlayer = if (currentPlayer == "X") "O" else "X"
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(cell, fontSize = 32.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (winner != null) {
            Text("Ganador: $winner", fontSize = 24.sp, color = Color.Red)
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = { resetGame() }) {
                Text("Reiniciar")
            }
            Spacer(Modifier.width(16.dp))
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}
