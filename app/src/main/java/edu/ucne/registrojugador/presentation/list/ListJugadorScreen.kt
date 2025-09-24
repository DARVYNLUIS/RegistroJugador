// file: edu/ucne/registrojugador/presentation/list/ListJugadorScreen.kt
package edu.ucne.registrojugador.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrojugador.domain.jugador.model.Jugador

@Composable
fun ListJugadorScreen(
    viewModel: ListJugadorViewModel = hiltViewModel(),
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToGame: () -> Unit,
    onNavigateToGamesList: () -> Unit,
    onNavigateToCreateLogro: () -> Unit,
    onNavigateToLogroList: () -> Unit // ¡Nuevo parámetro de navegación!
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListJugadorBody(
        state = state,
        onNavigateToEdit = onNavigateToEdit,
        onNavigateToCreate = onNavigateToCreate,
        onNavigateToGame = onNavigateToGame,
        onNavigateToGamesList = onNavigateToGamesList,
        onNavigateToCreateLogro = onNavigateToCreateLogro,
        onNavigateToLogroList = onNavigateToLogroList, // Pasa el nuevo parámetro
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListJugadorBody(
    state: ListJugadorUiState,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToGame: () -> Unit,
    onNavigateToGamesList: () -> Unit,
    onNavigateToCreateLogro: () -> Unit,
    onNavigateToLogroList: () -> Unit, // Nuevo parámetro
    onEvent: (ListJugadorUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Jugadores") },
                actions = {
                    IconButton(onClick = onNavigateToGamesList) {
                        Icon(Icons.Default.List, contentDescription = "Ver Partidas")
                    }
                    IconButton(onClick = onNavigateToLogroList) {
                        Icon(Icons.Default.Stars, contentDescription = "Ver Logros")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Jugador")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.jugadores) { jugador ->
                        JugadorCard(
                            jugador = jugador,
                            onEdit = { onNavigateToEdit(jugador.jugadorId) },
                            onDelete = { onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId)) },
                            onPlay = onNavigateToGame
                        )
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun JugadorCard(
    jugador: Jugador,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onPlay: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlay() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = jugador.nombres,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Partidas: ${jugador.partidas}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}