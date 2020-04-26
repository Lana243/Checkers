class CheckersGame (val model : CheckersModel, playerWhite : BasePlayer, playerBlack : BasePlayer) {
    private val players = Array<BasePlayer>(2) { i -> if (i == 0) playerWhite else playerBlack}

    fun play() {
        var moves = 0
        var queenMovesInRow = 0
        while (model.gameState == GameState.PLAYING) {
            //model.board.print()
            val turn = players[if (model.whoMoves == Color.WHITE) 0 else 1].makeTurn(model)
            //TODO("Add checking that game is not ended (state, updateState())
            val canMoveResult = model.canMove(turn)
            if (canMoveResult == null) {
                println("Illegal turn. Please, try again")
            } else {
                moves++
                if (model.board[turn.from].figure?.type == FigureType.Queen &&
                        model.board[turn.from] == canMoveResult)
                    queenMovesInRow++
                else
                    queenMovesInRow = 0
                model.move(turn)
            }
            if (queenMovesInRow > 30) {
                model.gameState = GameState.DRAW
            }
        }
        println(model.gameState.toString())
    }
}