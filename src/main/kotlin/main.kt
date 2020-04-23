fun main(args: Array<String>){
    var b = 0
    var w = 0
    for (i in 0 until 20) {
        val game = CheckersGame(CheckersModel(), MinimaxAlphaBetaPlayer("White Player", Color.WHITE), MinimaxPlayer("Black Player", Color.BLACK))
        game.play()
        if (game.model.gameState == GameState.BLACK_WINS) {
            b++
        } else {
            if (game.model.gameState == GameState.WHITE_WINS)
                w++
        }
        println("$w : $b")
    }
}

