package edu.ucne.registrojugador.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugador.domain.jugador.model.Player
import edu.ucne.registrojugador.domain.jugador.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListPlayerState())
    val state: StateFlow<ListPlayerState> = _state.asStateFlow()

    init {
        loadPlayers()
    }

    fun loadPlayers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val players = repository.getPlayers()
                _state.update { it.copy(players = players) }
            } catch (e: Exception) {
                _state.update { it.copy(message = e.message) }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun addPlayer(player: Player) {
        viewModelScope.launch {
            val success = repository.addPlayer(player)
            if (success) loadPlayers()
        }
    }

}