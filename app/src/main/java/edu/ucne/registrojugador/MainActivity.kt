package edu.ucne.registrojugador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registrojugador.presentation.jugador.edit.EditJugadorScreen
import edu.ucne.registrojugador.presentation.jugador.list.ListJugadorScreen
import edu.ucne.registrojugador.ui.theme.RegistroJugadorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroJugadorTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Registro de Jugadores") })
                    }
                ) { innerPadding ->
                    AppNavHost(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier
    ) {
        composable("list") {
            ListJugadorScreen(
                onNavigateToEdit = { jugadorId ->
                    // Si es 0 o null, es para crear un jugador nuevo
                    navController.navigate("edit/${jugadorId ?: 0}")
                },
                onNavigateToCreate = {
                    // Crear jugador
                    navController.navigate("edit/0")
                }
            )
        }

        composable(
            route = "edit/{jugadorId}",
            arguments = listOf(navArgument("jugadorId") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val jugadorId = backStackEntry.arguments?.getInt("jugadorId")
            EditJugadorScreen(
                jugadorId = if (jugadorId == 0) null else jugadorId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
