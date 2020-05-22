package gui

import core.BasePlayer
import core.CheckersModel
import core.Color
import core.GameState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.logging.Level
import java.util.logging.Logger

class CheckersGUIGame(val model: CheckersModel, val board: CheckersScreen.Board, playerWhite: BasePlayer<CheckersModel>, playerBlack: BasePlayer<CheckersModel>) {
    private val players = Array(2) { i -> if (i == 0) playerWhite else playerBlack }

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    suspend fun play(): GameState {
        while (model.gameState == GameState.PLAYING) {
            val turn = GlobalScope.async { players[if (model.whoMoves == Color.WHITE) 0 else 1].makeTurn(model) }
            val turnResult = turn.await()
            logger.log(Level.INFO, "Turn: $turnResult")
            val canMoveResult = model.canMove(turnResult)
            if (canMoveResult == null) {
                //println("Illegal turn. Please, try again")
            } else {
                board.turn(turnResult)
                if (canMoveResult != model.board[turnResult.from]) {
                    delay(1000)
                    board.eat(canMoveResult.X, canMoveResult.Y)
                }
                val typeBefore = model.board[turnResult.from].figure?.type
                model.move(turnResult)
                if (typeBefore != model.board[turnResult.to].figure?.type) {
                    board.becomeQueen(turnResult.to.first, turnResult.to.second)
                }
            }
        }
        board.changeState(model.gameState)
        return model.gameState
//        println(model.gameState.toString())
    }
}