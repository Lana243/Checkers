fun main(args: Array<String>){
    val game = CheckersGame(CheckersModel(), ConsoleUIPlayer("White Player", Color.WHITE), MinimaxAlphaBetaPlayer("Black Player", Color.BLACK))
    game.play()
}

