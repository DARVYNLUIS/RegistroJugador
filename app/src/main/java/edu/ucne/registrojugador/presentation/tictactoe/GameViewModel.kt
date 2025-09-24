package edu.ucne.registrojugador.presentation.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.domain.jugador.usecase.ObserveJugadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class GameUiState(
    val jugadores: List<Jugador> = emptyList(),
    val player1Id: Int? = null,
    val player2Id: Int? = null,
    val currentPlayerId: Int? = null,
    val board: List<Int?> = List(9) { null },
    val winnerId: Int? = null,
    val isDraw: Boolean = false,
    val gameStarted: Boolean = false,
    val esFinalizada: Boolean = false
)

@HiltViewModel
class GameViewModel @Inject constructor(
    private val observeJugadoresUseCase: ObserveJugadorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeJugadoresUseCase().collectLatest { list ->
                _state.update { it.copy(jugadores = list) }
            }
        }
    }

    fun selectPlayer1(jugador: Jugador) {
        _state.update { it.copy(player1Id = jugador.jugadorId) }
    }

    fun selectPlayer2(jugador: Jugador) {
        _state.update { it.copy(player2Id = jugador.jugadorId) }
    }

    fun startGame() {
        val s = state.value
        val p1 = s.player1Id
        val p2 = s.player2Id
        if (p1 == null || p2 == null || p1 == p2) return

        _state.update {
            it.copy(
                board = List(9) { null },
                winnerId = null,
                isDraw = false,
                currentPlayerId = p1,
                gameStarted = true,
                esFinalizada = false
            )
        }
    }

    fun onCellClick(index: Int) {
        val s = state.value
        if (!s.gameStarted || s.board[index] != null || s.winnerId != null || s.esFinalizada) return

        val current = s.currentPlayerId ?: return
        val newBoard = s.board.toMutableList()
        newBoard[index] = current

        val winner = checkWinner(newBoard)
        if (winner != null) {
            _state.update { it.copy(board = newBoard, winnerId = winner, esFinalizada = true) }
            return
        }

        if (newBoard.all { it != null }) {
            _state.update { it.copy(board = newBoard, isDraw = true, esFinalizada = true) }
            return
        }

        val next = if (current == s.player1Id) s.player2Id else s.player1Id
        _state.update { it.copy(board = newBoard, currentPlayerId = next) }
    }

    private fun checkWinner(board: List<Int?>): Int? {
        val lines = arrayOf(
            intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
            intArrayOf(0,4,8), intArrayOf(2,4,6)
        )
        for (line in lines) {
            val a = board[line[0]] ?: continue
            val b = board[line[1]] ?: continue
            val c = board[line[2]] ?: continue
            if (a == b && b == c) return a
        }
        return null
    }

    fun restartGame() {
        _state.update {
            it.copy(
                board = List(9) { null },
                winnerId = null,
                isDraw = false,
                currentPlayerId = it.player1Id,
                gameStarted = it.player1Id != null && it.player2Id != null,
                esFinalizada = false
            )
        }
    }
}