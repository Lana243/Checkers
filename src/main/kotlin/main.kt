fun main(args: Array<String>){
    val game = CheckersGame(CheckersModel(), MinimaxAlphaBetaPlayer("White Player", Color.WHITE), MinimaxPlayer("Black Player", Color.BLACK))
    game.play()
}

