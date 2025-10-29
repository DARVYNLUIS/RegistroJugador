package edu.ucne.registrojugador.presentation.navigation

object Screen {

    object PlayerList {
        const val route = "player_list"
    }
    object JugadorList {
        const val route = "jugador_list"
    }

    object TicTacToe {
        const val route = "tic_tac_toe/{partidaId}"

        fun createRoute(partidaId: Int?): String {
            return "tic_tac_toe/${partidaId ?: -1}"
        }
    }

    object PartidaList {
        const val route = "partida_list"
    }

    object EditPlayer {
        const val route = "edit_jugador/{jugadorId}"

        fun createRoute(jugadorId: Int?): String {
            return "edit_jugador/${jugadorId ?: -1}"
        }
    }

    object EditPartida {
        const val route = "edit_partida/{partidaId}"

        fun createRoute(partidaId: Int?): String {
            return "edit_partida/${partidaId ?: -1}"
        }
    }
}
