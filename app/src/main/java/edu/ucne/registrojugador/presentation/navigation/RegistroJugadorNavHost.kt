package edu.ucne.registrojugador.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registrojugador.presentation.EditJugadorScreen
import edu.ucne.registrojugador.presentation.tictactoe.TicTacToeScreen
import edu.ucne.registrojugador.presentation.list.ListJugadorScreen
import edu.ucne.registrojugador.presentation.partida.ListPartidaScreen
import edu.ucne.registrojugador.presentation.partida.PartidaViewModel
import edu.ucne.registrojugador.presentation.logro.list.ListLogroScreen
import edu.ucne.registrojugador.presentation.logro.edit.EditLogroScreen
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
        composable(Screen.JugadorList.route) {
            ListJugadorScreen(
                onNavigateToGame = { navController.navigate(Screen.TicTacToe.route) },
                onNavigateToGamesList = { navController.navigate(Screen.PartidaList.route) },
                onNavigateToCreate = { navController.navigate(Screen.EditJugador.createRoute(null)) },
                onNavigateToEdit = { jugadorId ->
                    navController.navigate(Screen.EditJugador.createRoute(jugadorId))
                },
                onNavigateToCreateLogro = { navController.navigate(Screen.EditLogro.createRoute(null)) },
                onNavigateToLogroList = { navController.navigate(Screen.LogroList.route) } // Nuevo parámetro de navegación
            )
        }

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

        composable(Screen.LogroList.route) {
            ListLogroScreen(
                onNavigateToEdit = { logroId ->
                    navController.navigate(Screen.EditLogro.createRoute(logroId))
                },
                onNavigateToCreate = { navController.navigate(Screen.EditLogro.createRoute(null)) }
            )
        }

        composable(Screen.EditLogro.route) {
            EditLogroScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}