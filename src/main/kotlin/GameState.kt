enum class GameState {
    PLAYING,
    WHITE_WINS,
    BLACK_WINS,
    DRAW
}

fun GameState.getColor() : Color? {
    return when (this) {
        GameState.PLAYING, GameState.DRAW ->
           null
        GameState.WHITE_WINS -> Color.WHITE
        GameState.BLACK_WINS -> Color.BLACK
    }
}