package edu.ucne.registrojugador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
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
                Scaffold (
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Lista de jugadores",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                        )
                    },
                ){
                    Column (
                        modifier = Modifier.fillMaxSize()
                            .padding(it)
                    ) {
                        EditJugadorScreen()
                        ListJugadorScreen()
                    }

                }


            }
        }
    }
}