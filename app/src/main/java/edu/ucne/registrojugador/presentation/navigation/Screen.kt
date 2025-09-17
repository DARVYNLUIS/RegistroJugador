package edu.ucne.registrojugador.presentation.navigation

sealed class Screen(val route: String) {
    // Rutas sin parámetros (Objetos)
    object JugadorList : Screen("jugador_list")
    object PartidaList : Screen("partida_list")
    object TicTacToe : Screen("tic_tac_toe")

    // Rutas con parámetros (Objetos y funciones de ayuda)
    object EditJugador : Screen("edit_jugador/{jugadorId}") {
        fun createRoute(jugadorId: Int?) = "edit_jugador/${jugadorId ?: "null"}"
    }

    object EditPartida : Screen("edit_partida/{partidaId}") {
        fun createRoute(partidaId: Int?) = "edit_partida/${partidaId ?: "null"}"
    }
}