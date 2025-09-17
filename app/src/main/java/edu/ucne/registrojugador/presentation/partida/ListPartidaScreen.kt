package edu.ucne.registrojugador.presentation.partida

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrojugador.domain.jugador.model.Partida

@Composable
fun ListPartidaScreen(
    viewModel: PartidaViewModel = hiltViewModel(),
    onNavigateToCreate: () -> Unit,
    onBack: () -> Unit // Agregado: el parámetro 'onBack'
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListPartidaBody(
        state = state,
        onNavigateToCreate = onNavigateToCreate,
        onDeletePartida = viewModel::eliminarPartida
    )
}

@Composable
fun ListPartidaBody(
    state: PartidaUiState,
    onNavigateToCreate: () -> Unit,
    onDeletePartida: (Partida) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Button to create a new match
            Button(
                onClick = onNavigateToCreate,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Partida")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of matches
            if (state.partidas.isEmpty()) {
                Text("No hay partidas registradas.", style = MaterialTheme.typography.titleMedium)
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.partidas) { partida ->
                        PartidaCard(
                            partida = partida,
                            onDelete = { onDeletePartida(partida) }
                        )
                    }
                }
            }
        }

        // Display messages using Snackbar
        state.message?.let {
            Snackbar {
                Text(it)
            }
        }
    }
}

@Composable
fun PartidaCard(
    partida: Partida,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("ID Partida: ${partida.partidaId}", fontWeight = FontWeight.Bold)
                Text("Fecha: ${partida.fecha}")
                Text("Jugador 1 ID: ${partida.jugador1Id}")
                Text("Jugador 2 ID: ${partida.jugador2Id}")
                val ganadorText = if (partida.ganadorId != null) "Ganador: ${partida.ganadorId}" else "Empate"
                Text(ganadorText)
                val estadoText = if (partida.esFinalizada) "Finalizada" else "En curso"
                Text("Estado: $estadoText")
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Partida")
            }
        }
    }
}