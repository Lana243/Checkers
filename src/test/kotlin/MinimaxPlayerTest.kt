import org.junit.jupiter.api.Test

class MinimaxPlayerTest {
    @Test
    fun `Test AlphaBeta wins Minimax`() {
        var b = 0
        var w = 0
        for (i in 0 until 5) {
            val game = CheckersGame(CheckersModel(), MinimaxAlphaBetaPlayer("White Player", Color.WHITE), MinimaxPlayer("Black Player", Color.BLACK))
            game.play()
            when (game.model.gameState) {
                GameState.BLACK_WINS -> b += 2
                GameState.WHITE_WINS -> w += 2
                GameState.DRAW -> {b++; w++}
            }
        }
        assert(w >= 6)
    }
}