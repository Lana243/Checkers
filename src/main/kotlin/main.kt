fun main(args: Array<String>){
    val game = CheckersGame(CheckersModel(), ConsoleUIPlayer("White Player", Color.WHITE), ConsoleUIPlayer("Black Player", Color.BLACK))
    game.game()
}