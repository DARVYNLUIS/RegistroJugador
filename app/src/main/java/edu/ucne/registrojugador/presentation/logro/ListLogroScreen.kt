package edu.ucne.registrojugador.presentation.logro.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrojugador.domain.jugador.model.Logro
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListLogroScreen(
    viewModel: ListLogroViewModel = hiltViewModel(),
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Logros") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Crear nuevo logro")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error?.isNotEmpty() == true) {
                Text("Error: ${uiState.error}", modifier = Modifier.align(Alignment.Center))
            } else {
                if (uiState.logros.isEmpty()) {
                    Text(
                        text = "No hay logros. ¡Crea uno!",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.logros) { logro ->
                            LogroCard(
                                logro = logro,
                                onEdit = { onNavigateToEdit(logro.logroId) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LogroCard(logro: Logro, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onEdit)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "ID: ${logro.logroId}", style = MaterialTheme.typography.bodySmall)
            Text(text = logro.nombre, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Text(text = logro.descripcion, style = MaterialTheme.typography.bodyMedium)
        }
    }
}