package edu.ucne.registrojugador.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import edu.ucne.registrojugador.presentation.list.ListJugadorScreen
import edu.ucne.registrojugador.presentation.partida.ListPartidaScreen
import edu.ucne.registrojugador.presentation.partida.PartidaViewModel
import edu.ucne.registrojugador.presentation.tictactoe.TicTacToeScreen
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
                onNavigateToEdit = { jugadorId ->
                    navController.navigate(Screen.EditJugador.createRoute(jugadorId))
                },
                onNavigateToCreate = {
                    navController.navigate(Screen.EditJugador.createRoute(null))
                },
                onNavigateToGame = {
                    navController.navigate(Screen.TicTacToe.createRoute(null))
                },
                onNavigateToGamesList = {
                    navController.navigate(Screen.PartidaList.route)
                }
            )
        }

        composable(
            route = Screen.TicTacToe.route,
            arguments = listOf(
                navArgument("partidaId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val partidaIdArg = backStackEntry.arguments?.getInt("partidaId") ?: -1
            val partidaId = if (partidaIdArg == -1) null else partidaIdArg

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
                navToDetalle = { partidaId ->
                    navController.navigate(Screen.TicTacToe.createRoute(partidaId))
                },
                viewModel = partidaViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditJugador.route,
            arguments = listOf(navArgument("jugadorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val jugadorIdArg = backStackEntry.arguments?.getInt("jugadorId") ?: -1
            val jugadorId = if (jugadorIdArg == -1) null else jugadorIdArg

        }

        composable(
            route = Screen.EditPartida.route,
            arguments = listOf(navArgument("partidaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val partidaIdArg = backStackEntry.arguments?.getInt("partidaId") ?: -1
            val partidaId = if (partidaIdArg == -1) null else partidaIdArg

        }
    }
}
