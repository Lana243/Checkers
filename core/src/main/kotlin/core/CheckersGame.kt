package core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CheckersGame(val model: CheckersModel, playerWhite: BasePlayer<CheckersModel>, playerBlack: BasePlayer<CheckersModel>) {
    private val players = Array(2) { i -> if (i == 0) playerWhite else playerBlack }

    /*fun play() {
        while (model.gameState == GameState.PLAYING) {
            model.board.print()
            val turn = GlobalScope.async { players[if (model.whoMoves == Color.WHITE) 0 else 1].makeTurn(model) }
            val turnResult = turn.await()
            val canMoveResult = model.canMove(turnResult)
            if (canMoveResult == null) {
                println("Illegal turn. Please, try again")
            } else {
                model.move(turnResult)
            }
        }
        println(model.gameState.toString())
    }*/
}