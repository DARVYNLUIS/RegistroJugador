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
import edu.ucne.registrojugador.presentation.list.ListPlayerScreen
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
        startDestination = Screen.PlayerList.route, // Abrir con la lista de players
        modifier = modifier
    ) {

        // Lista de Players (desde API)
        composable(Screen.PlayerList.route) {
            ListPlayerScreen(
                onNavigateToEdit = { playerId ->
                    navController.navigate(Screen.EditPlayer.createRoute(playerId))
                },
                onNavigateToCreate = {
                    navController.navigate(Screen.EditPlayer.createRoute(null))
                }
            )
        }

        // TicTacToe
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

        // Lista de Partidas
        composable(Screen.PartidaList.route) {
            ListPartidaScreen(
                navToDetalle = { partidaId ->
                    navController.navigate(Screen.TicTacToe.createRoute(partidaId))
                },
                viewModel = partidaViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // Edit Player
        composable(
            route = Screen.EditPlayer.route,
            arguments = listOf(navArgument("playerId") { type = NavType.IntType })
        ) { backStackEntry ->
            val playerIdArg = backStackEntry.arguments?.getInt("playerId") ?: -1
            val playerId = if (playerIdArg == -1) null else playerIdArg

            // Aquí iría tu pantalla de edición de Player
        }

        // Edit Partida
        composable(
            route = Screen.EditPartida.route,
            arguments = listOf(navArgument("partidaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val partidaIdArg = backStackEntry.arguments?.getInt("partidaId") ?: -1
            val partidaId = if (partidaIdArg == -1) null else partidaIdArg

            // Aquí iría tu pantalla de edición de Partida
        }
    }
}