// file: C:/APLICADA_2/app/src/main/java/edu/ucne/registrojugador/presentation/navigation/RegistroNavHost.kt

package edu.ucne.registrojugador.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registrojugador.presentation.jugador.edit.EditJugadorScreen
import edu.ucne.registrojugador.presentation.juego.TicTacToeScreen
import edu.ucne.registrojugador.presentation.list.ListJugadorScreen
import edu.ucne.registrojugador.presentation.partida.ListPartidaScreen
import edu.ucne.registrojugador.presentation.partida.PartidaViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun RegistroNavHost(
    navController: NavHostController,
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val partidaViewModel: PartidaViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.JugadorList.route,
        modifier = modifier
    ) {

        // Pantalla de lista de jugadores
        composable(Screen.JugadorList.route) {
            ListJugadorScreen(
                onNavigateToGame = { navController.navigate(Screen.TicTacToe.route) },
                onNavigateToGamesList = { navController.navigate(Screen.PartidaList.route) },
                onNavigateToCreate = { navController.navigate(Screen.EditJugador.createRoute(null)) },
                onNavigateToEdit = { jugadorId ->
                    navController.navigate(Screen.EditJugador.createRoute(jugadorId))
                }
            )
        }

        // Pantalla del juego
        composable(Screen.TicTacToe.route) {
            TicTacToeScreen(
                onBack = { navController.popBackStack() },
                onGameEnd = { partida ->
                    partidaViewModel.agregarPartida(partida)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.PartidaList.route) {
            ListPartidaScreen(
                onNavigateToCreate = {
                    navController.navigate(Screen.TicTacToe.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.EditJugador.route) {
            EditJugadorScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
