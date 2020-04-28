enum class GameState {
    PLAYING,
    WHITE_WINS,
    BLACK_WINS,
    DRAW;

    override fun toString(): String {
        return when (this) {
            BLACK_WINS -> "Black wins"
            WHITE_WINS -> "White wins"
            DRAW -> "Draw"
            PLAYING -> "Game isn't ended"
        }
    }
}

fun GameState.winnerColor() : Color? {
    return when (this) {
        GameState.PLAYING, GameState.DRAW ->
           null
        GameState.WHITE_WINS -> Color.WHITE
        GameState.BLACK_WINS -> Color.BLACK
    }
}