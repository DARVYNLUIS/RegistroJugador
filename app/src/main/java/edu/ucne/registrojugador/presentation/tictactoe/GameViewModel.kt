package edu.ucne.registrojugador.presentation.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.presentation.partida.MovimientosViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameState(
    val board: List<Int?> = List(9) { null },
    val player1Id: Int? = null,
    val player2Id: Int? = null,
    val currentPlayerId: Int? = null,
    val winnerId: Int? = null,
    val isDraw: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val esFinalizada: Boolean = false
)

class GameViewModel : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state

    fun startGame(player1: Jugador, player2: Jugador) {
        _state.update {
            it.copy(
                board = List(9) { null },
                player1Id = player1.jugadorId,
                player2Id = player2.jugadorId,
                currentPlayerId = player1.jugadorId,
                winnerId = null,
                isDraw = false,
                esFinalizada = false
            )
        }
    }

    fun onCellClick(index: Int, movimientosViewModel: MovimientosViewModel) {
        viewModelScope.launch {
            val s = _state.value
            if (s.board[index] != null || s.esFinalizada) return@launch

            val newBoard = s.board.toMutableList()
            newBoard[index] = s.currentPlayerId
            val nextPlayer = if (s.currentPlayerId == s.player1Id) s.player2Id else s.player1Id

            val winner = checkWinner(newBoard, s.player1Id, s.player2Id)
            val isDraw = newBoard.all { it != null } && winner == null

            _state.update {
                it.copy(
                    board = newBoard,
                    currentPlayerId = if (winner == null && !isDraw) nextPlayer else null,
                    winnerId = winner,
                    isDraw = isDraw,
                    esFinalizada = winner != null || isDraw
                )
            }


        }
    }

    fun restartGame() {
        _state.update {
            it.copy(
                board = List(9) { null },
                currentPlayerId = it.player1Id,
                winnerId = null,
                isDraw = false,
                esFinalizada = false
            )
        }
    }

    private fun checkWinner(board: List<Int?>, p1: Int?, p2: Int?): Int? {
        val lines = listOf(
            listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
            listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
            listOf(0,4,8), listOf(2,4,6)
        )
        for (line in lines) {
            val (a,b,c) = line
            if (board[a] != null && board[a] == board[b] && board[a] == board[c]) {
                return board[a]
            }
        }
        return null
    }
}
