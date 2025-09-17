package edu.ucne.registrojugador.presentation.jugador.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun EditJugadorScreen(
    jugadorId: Int? = null,
    viewModel: EditJugadorViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(jugadorId) {
        jugadorId?.let { viewModel.onEvent(EditJugadorUiEvent.Load(it)) }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Campo Nombres
            OutlinedTextField(
                value = state.nombres,
                onValueChange = { viewModel.onEvent(EditJugadorUiEvent.NombresChanged(it)) },
                label = { Text("Nombre del Jugador") },
                isError = state.nombresError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nombresError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Partidas (Int)
            OutlinedTextField(
                value = state.partidas, // Use the String value from the state
                onValueChange = { text ->
                    viewModel.onEvent(EditJugadorUiEvent.PartidasChanged(text)) // Pass the String directly
                },
                label = { Text("Partidas Jugadas") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.partidasError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.partidasError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones Guardar y Eliminar
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { viewModel.onEvent(EditJugadorUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) { Text("Guardar") }

                Spacer(modifier = Modifier.width(8.dp))

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { viewModel.onEvent(EditJugadorUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.weight(1f)
                    ) { Text("Eliminar") }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Volver
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ver Lista de Jugadores") }
        }
    }

    // Snackbar para mensajes de estado
    LaunchedEffect(state.message) {
        state.message?.let {
            scope.launch { snackbarHostState.showSnackbar(it) }
            viewModel.clearMessage()
        }
    }
}