class CheckersGame (val model : CheckersModel, playerWhite : BasePlayer<CheckersModel>, playerBlack : BasePlayer<CheckersModel>) {
    private val players = Array(2) { i -> if (i == 0) playerWhite else playerBlack}

    fun play() {
        while (model.gameState == GameState.PLAYING) {
            //model.board.print()
            val turn = players[if (model.whoMoves == Color.WHITE) 0 else 1].makeTurn(model)
            //TODO("Add checking that game is not ended (state, updateState())
            val canMoveResult = model.canMove(turn)
            if (canMoveResult == null) {
                println("Illegal turn. Please, try again")
            } else {
                model.move(turn)
            }
        }
        println(model.gameState.toString())
    }
}