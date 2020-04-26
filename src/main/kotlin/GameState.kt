enum class GameState {
    PLAYING,
    WHITE_WINS,
    BLACK_WINS,
    DRAW
}

fun GameState.winnerColor() : Color? {
    return when (this) {
        GameState.PLAYING, GameState.DRAW ->
           null
        GameState.WHITE_WINS -> Color.WHITE
        GameState.BLACK_WINS -> Color.BLACK
    }
}

fun GameState.toString() : String {
    return when (this) {
        GameState.BLACK_WINS -> "Black wins"
        GameState.WHITE_WINS -> "White wins"
        GameState.DRAW -> "Draw"
        GameState.PLAYING -> "Game isn't ended"
    }
}