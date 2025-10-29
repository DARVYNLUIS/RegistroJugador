package edu.ucne.registrojugador.presentation.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.model.Partida
import edu.ucne.registrojugador.presentation.partida.MovimientosViewModel
import java.time.LocalDate

@Composable
fun TicTacToeScreen(
    gameViewModel: GameViewModel = hiltViewModel(),
    movimientosViewModel: MovimientosViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onGameEnd: (Partida) -> Unit
) {
    val state by gameViewModel.state.collectAsState()
    val movimientosState by movimientosViewModel.state.collectAsState()
    var partidaIdInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo para ID de partida
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = partidaIdInput,
                onValueChange = { partidaIdInput = it },
                label = { Text("ID de Partida") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (partidaIdInput.isNotEmpty()) {
                    val p1 = Jugador(JugadorId = 1, nombres = "Jugador 1")
                    val p2 = Jugador(JugadorId = 2, nombres = "Jugador 2")
                    gameViewModel.startGame(p1, p2, partidaIdInput.toInt())
                }
            }) {
                Text("Iniciar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Estado del juego
        val jugadoresMap = state.jugadores.associateBy { it.JugadorId }
        val currentPlayerName = state.currentPlayerId?.let { jugadoresMap[it]?.nombres } ?: "—"
        val statusText = when {
            state.winnerId != null -> "¡Ganador: ${jugadoresMap[state.winnerId]?.nombres}!"
            state.isDraw -> "¡Empate!"
            else -> "Turno de: $currentPlayerName"
        }

        Text(text = statusText, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Tablero
        GameBoard(
            board = state.board,
            jugadoresMap = jugadoresMap as Map<Int, Jugador?>,
            player1Id = state.player1Id,
            player2Id = state.player2Id
        ) { index ->
            gameViewModel.onCellClick(index, movimientosViewModel)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { gameViewModel.restartGame() }) { Text("Reiniciar Juego") }
            Button(onClick = onBack) { Text("Volver") }
        }

        movimientosState.message?.let { message ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(message, color = Color.Red, fontWeight = FontWeight.Bold)
        }

        LaunchedEffect(state.esFinalizada) {
            if (state.esFinalizada && state.player1Id != null && state.player2Id != null) {
                val partida = Partida(
                    fecha = LocalDate.now().toString(),
                    jugador1Id = state.player1Id!!,
                    jugador2Id = state.player2Id!!,
                    ganadorId = state.winnerId,
                    esFinalizada = true
                )
                onGameEnd(partida)
            }
        }
    }
}

@Composable
fun GameBoard(
    board: List<Int?>,
    jugadoresMap: Map<Int, Jugador?>,
    player1Id: Int?,
    player2Id: Int?,
    onCellClick: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    val index = row * 3 + col
                    val jugador = board[index]?.let { jugadoresMap[it] }
                    val isP1 = board[index] != null && board[index] == player1Id
                    BoardCell(jugador, isP1) {
                        if (board[index] == null) onCellClick(index)
                    }
                }
            }
        }
    }
}

@Composable
fun BoardCell(
    jugador: Jugador?,
    isPlayer1: Boolean,
    onClick: () -> Unit
) {
    val display = when {
        jugador == null -> ""
        isPlayer1 -> "X"
        else -> "O"
    }

    val color = when {
        jugador == null -> Color.Black
        isPlayer1 -> Color(0xFF0D47A1)
        else -> Color(0xFFD32F2F)
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .background(Color.LightGray)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(display, fontSize = 48.sp, fontWeight = FontWeight.Bold, color = color)
    }
}