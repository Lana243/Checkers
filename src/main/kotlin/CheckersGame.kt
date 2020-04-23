class CheckersGame (val model : CheckersModel, playerWhite : BasePlayer, playerBlack : BasePlayer) {
    private val players = Array<BasePlayer>(2) { i -> if (i == 0) playerWhite else playerBlack}

    fun play() {
        var moves = 0
        while (model.gameState == GameState.PLAYING) {
            //model.board.print()
            val turn = players[if (model.whoMoves == Color.WHITE) 0 else 1].makeTurn(model)
            //TODO("Add checking that game is not ended (state, updateState())
            if (model.canMove(turn) == null) {
                println("Illegal turn. Please, try again")
            } else {
                model.move(turn)
                moves++
            }
            if (moves > 150)
                break
        }
        println(model.gameState.toString())
    }
}