package gui

import core.BaseTurn
import core.CheckersModel
import core.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.logging.Level
import java.util.logging.Logger

class GUIPlayer(color: Color) : core.BasePlayer<CheckersModel>(color) {

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    private var posX: Int? = null
    private var posY: Int? = null
    private var lastTurn: Boolean = false

    private suspend fun receive(): Pair<Int, Int> {
        logger.log(Level.INFO, "Start send()")
        lastTurn = false
        /*do {
            while (!lastTurn) {
                //delay(100)
            }
            logger.log(Level.INFO, "lastTurn == true")
        } while (posX == null || posY == null)
        return (posX ?: throw Exception()) to (posY ?: throw Exception())*/
        while (!lastTurn) {
            delay(100)
        }
        return posX!! to posY!!
    }

    override suspend fun makeTurn(model: CheckersModel): BaseTurn {
        logger.log(Level.INFO, "Start to wait player's turn")
        val from = GlobalScope.async { receive() }
        from.join()
        logger.log(Level.INFO, "Turn from: received")
        val to = GlobalScope.async { receive() }
        val turn = BaseTurn(color, from.await(), to.await())
        logger.log(Level.INFO, "Turn to: received")
        return turn
    }

    fun setXY(x: Int, y: Int) {
        logger.log(Level.INFO, "Set $x, $y")
        posX = x
        posY = y
        lastTurn = true
    }

}
