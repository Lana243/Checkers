class CheckersGame (private val model : CheckersModel, playerWhite : BasePlayer, playerBlack : BasePlayer) {
    private val players = Array<BasePlayer>(2) { i -> if (i == 0) playerWhite else playerBlack}

    fun game() {
        while (true) {
            model.board.print()
            val turn = players[if (model.whoMoves == 1) 0 else 1].makeTurn(model)
            if (turn == null) {
                println("Invalid format. Please, try again")
            } else {
                if (model.canMove(turn) == null) {
                    println("Illegal turn. Please, try again")
                } else {
                    model.move(turn)
                }
            }
        }
    }
}