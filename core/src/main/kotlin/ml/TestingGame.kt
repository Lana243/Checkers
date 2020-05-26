package ml

import core.*
import java.lang.Exception
import java.util.logging.Logger

class TestingGame(private val model: CheckersModel, playerWhite: BasePlayer<CheckersModel>, playerBlack: BasePlayer<CheckersModel>): BaseGame {
    private val players = Array(2) { i -> if (i == 0) playerWhite else playerBlack }

    init {
        for (player in players) {
            player.setGame(this)
        }
    }

    fun start() {
        players[0].update()
    }

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
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
        model.move(turn)
        players[if (model.whoMoves == Color.WHITE) 0 else 1].update()
    }

    override fun getModel(): CheckersModel {
        return model
    }
}