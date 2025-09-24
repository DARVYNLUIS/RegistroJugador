package edu.ucne.registrojugador.presentation.navigation

sealed class Screen(val route: String) {
    object JugadorList : Screen("jugador_list")
    object PartidaList : Screen("partida_list")
    object TicTacToe : Screen("tic_tac_toe")
    object LogroList : Screen("logro_list")

    // Rutas con parámetros (Objetos y funciones de ayuda)
    object EditJugador : Screen("edit_jugador/{jugadorId}") {
        fun createRoute(jugadorId: Int?) = "edit_jugador/${jugadorId ?: 0}"
    }

    object EditPartida : Screen("edit_partida/{partidaId}") {
        fun createRoute(partidaId: Int?) = "edit_partida/${partidaId ?: 0}"
    }

    object EditLogro : Screen("edit_logro/{logroId}") {
        fun createRoute(logroId: Int?) = "edit_logro/${logroId ?: 0}"
    }
}