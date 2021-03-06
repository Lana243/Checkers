package gui

import core.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.logging.Logger

class CheckersGUIGame(private val model: CheckersModel, val board: CheckersScreen.Board, playerWhite: BasePlayer<CheckersModel>, playerBlack: BasePlayer<CheckersModel>) : BaseGame {
    private val players = arrayOf(playerWhite, playerBlack)

    init {
        for (player in players) {
            player.setGame(this)
        }
    }

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    fun start() {
        GlobalScope.launch {
            players[0].update()
        }
    }

    override fun makeTurn(turn: BaseTurn) {
        if (model.gameState != GameState.PLAYING) {
            throw Exception()
        }
        val canMoveResult = model.canMove(turn)
        if (canMoveResult == null) {
            players[if (model.whoMoves == Color.WHITE) 0 else 1].illegalTurn()
            return
        }

        val typeBefore = model.board[turn.from].figure?.type
        model.move(turn)

        GlobalScope.launch {
            delay(750)
            board.setColor(model.whoMoves)
        }

        board.turn(turn)
        if (canMoveResult != model.board[turn.from]) {
            Thread.sleep(1000)
            board.eat(canMoveResult.x, canMoveResult.y)
        }
        if (typeBefore != model.board[turn.to].figure?.type) {
            board.becomeQueen(turn.to.first, turn.to.second)
        }
        if (model.gameState == GameState.PLAYING) {
            GlobalScope.launch {
                delay(800)
                players[if (model.whoMoves == Color.WHITE) 0 else 1].update()
            }
        }
    }

    override fun getModel(): CheckersModel {
        return model
    }
}