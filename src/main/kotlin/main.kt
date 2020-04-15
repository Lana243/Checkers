fun main(args: Array<String>){
    val game = CheckersGame(CheckersModel(), ConsoleUIPlayer("White Player", 1), ConsoleUIPlayer("Black Player", -1))
    game.play()
}

