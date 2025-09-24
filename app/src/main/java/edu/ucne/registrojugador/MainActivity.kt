package edu.ucne.registrojugador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registrojugador.presentation.navigation.RegistroNavHost
import edu.ucne.registrojugador.ui.theme.RegistroJugadorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val scope: CoroutineScope = MainScope()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroJugadorTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Registro de Jugadores") }) }
                ) { innerPadding ->
                    AppContent(Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun AppContent(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        RegistroNavHost(
            navController = navController,
            scope = scope,
            modifier = modifier // Pass the modifier to the NavHost
        )
    }
}