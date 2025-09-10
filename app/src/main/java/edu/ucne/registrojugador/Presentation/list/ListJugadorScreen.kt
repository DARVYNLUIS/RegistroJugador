package edu.ucne.registrojugador.presentation.jugador.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrojugador.domain.jugador.model.Jugador
import edu.ucne.registrojugador.presentation.list.ListJugadorUiEvent
import edu.ucne.registrojugador.presentation.list.ListJugadorUiState
import edu.ucne.registrojugador.presentation.list.ListJugadorViewModel

@Composable
fun ListJugadorScreen(
    viewModel: ListJugadorViewModel = hiltViewModel(),
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToGame: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListJugadorBody(
        state = state,
        onNavigateToEdit = onNavigateToEdit,
        onNavigateToCreate = onNavigateToCreate,
        onNavigateToGame = onNavigateToGame,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListJugadorBody(
    state: ListJugadorUiState,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToGame: () -> Unit,
    onEvent: (ListJugadorUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onNavigateToCreate,
                modifier = Modifier.weight(1f)
            ) {
                Text("Crear Jugador")
            }
            Button(
                onClick = onNavigateToGame,
                modifier = Modifier.weight(1f)
            ) {
                Text("Jugar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.jugadores) { jugador ->
                    JugadorCard(
                        jugador = jugador,
                        onEdit = { onNavigateToEdit(jugador.jugadorId) },
                        onDelete = { onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId)) }
                    )
                }
            }
        }
    }
}

@Composable
fun JugadorCard(
    jugador: Jugador,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(jugador.nombres, style = MaterialTheme.typography.titleMedium)
                Text("Partidas: ${jugador.partidas}")
            }

            TextButton(onClick = onEdit) { Text("Editar") }
            TextButton(onClick = onDelete) { Text("Eliminar") }
        }
    }
}
