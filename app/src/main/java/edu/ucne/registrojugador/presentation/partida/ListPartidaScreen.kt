package edu.ucne.registrojugador.presentation.partida

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.registrojugador.domain.jugador.model.Partida

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPartidaScreen(
    navToDetalle: (Int) -> Unit,
    viewModel: PartidaViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()
    var showSnackbar by remember { mutableStateOf(false) }

    LaunchedEffect(state.message) {
        state.message?.let {
            showSnackbar = true
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = { Text("Partidas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.crearPartida(1, 2) }) { // Ajusta IDs según tu lógica
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                state.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                state.partidas.isEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("No hay partidas disponibles") }

                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.partidas.sortedByDescending { it.partidaId }) { partida ->
                        PartidaCard(
                            partida = partida,
                            onClick = { navToDetalle(partida.partidaId) }
                        )
                    }
                }
            }

            if (showSnackbar) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = {
                            showSnackbar = false
                            viewModel.clearMessage()
                        }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(text = state.message ?: "")
                }
            }
        }
    }
}

@Composable
fun PartidaCard(partida: Partida, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${partida.partidaId}", fontWeight = FontWeight.Bold)
            Text("Fecha: ${partida.fecha}")
            Text("Jugador 1 ID: ${partida.jugador1Id}")
            Text("Jugador 2 ID: ${partida.jugador2Id}")
            Text("Ganador: ${partida.ganadorId?.toString() ?: "Empate/Desconocido"}")
            Text("Estado: ${if (partida.esFinalizada) "Finalizada" else "En curso"}")
        }
    }
}
