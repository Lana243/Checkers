class CheckersGame (private val model : CheckersModel, playerWhite : BasePlayer, playerBlack : BasePlayer) {
    private val players = Array<BasePlayer>(2) { i -> if (i == 0) playerWhite else playerBlack}
    private var whoMoves = 0 //0 - white, 1 - black

    fun game() {
        while (true) {
            model.printBoardOnConsole()
            val turn = players[whoMoves].makeTurn(model)
            if (turn == null) {
                println("Invalid format. Please, try again")
            } else {
                if (model.canMove(turn) == null) {
                    println("Illegal turn. Please, try again")
                } else {
                    model.move(turn)
                    whoMoves = (whoMoves + 1) % 2
                }
            }
        }
    }
}