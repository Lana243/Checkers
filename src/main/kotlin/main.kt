fun main(args: Array<String>){
    val game = CheckersGame(CheckersModel(), MinimaxAlphaBetaPlayer("White Player", Color.WHITE), ConsoleUIPlayer("Black Player", Color.BLACK, 1))
    game.play()
}

