package core

import ml.PositionEvaluation
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.max
import kotlin.math.min

class MinimaxAlphaBetaPlayerWithVector(name: String, color: Color, private val maxDepth: Int, val vector: IntArray) : MinimaxAlphaBetaPlayer(name, color, maxDepth) {

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    override fun update() {
        makeTurn()
    }

    override fun illegalTurn() {}

    override fun makeTurn() {
        if (baseGame.getModel().possibleTurns().size == 1) {
            baseGame.makeTurn(baseGame.getModel().possibleTurns()[0])
            return
        }
        val tmpModel = CheckersModel(baseGame.getModel())
        recCount = 0
        val turn = minimaxAlphaBetaRecursive(tmpModel, 0, maxDepth).second ?: throw Exception("Can't find move")
        //logger.log(Level.INFO, "Number of recursive calls is $recCount")
        baseGame.makeTurn(turn)
    }

    private fun minimaxAlphaBetaRecursive(model: CheckersModel,
                                          depth: Int,
                                          maxDepth: Int,
                                          alpha: Int = MIN_VALUE,
                                          beta: Int = MIN_VALUE,
                                          changeColor: Boolean = false)
            : Pair<Int, BaseTurn?> {

        recCount++

        val isTerminalStateResult = isTerminalState(model, depth, maxDepth)
        if (isTerminalStateResult != null) {
            return isTerminalStateResult to null
        }

        // maybe, we already had this position in not so much depth, then we can use early result
        val turns = /*if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!).toMutableList()
        else*/
                model.possibleTurns().toMutableList()
        sortTurns(turns)

        var bestVal = MIN_VALUE
        var bestTurn = turns[0]
        for (turn in turns) {
            val retainer = ModelRetainer(model, turn)
            try {
                model.move(turn)
            } catch (e : IllegalStateException) {
                logger.log(Level.WARNING, turn.toString())
                model.board.print()
            }
            val ans = minimaxAlphaBetaRecursive(model,
                    depth + if (model.whoMoves == retainer.whoMoves) 0 else 1,
                    maxDepth,
                    if (model.whoMoves == retainer.whoMoves) alpha else max(bestVal, beta),
                    if (model.whoMoves == retainer.whoMoves) min(beta, -bestVal) else alpha,
                    model.whoMoves != retainer.whoMoves).first * (if (model.whoMoves == retainer.whoMoves) 1 else -1)
            retainer.reset(model)
            /*if (depth <= 0) {
                logger.log(Level.INFO, turn.toString() + " : " + ans)
            }*/
            if (ans > bestVal) {
                bestVal = ans
                bestTurn = turn
            }
            if (changeColor && -bestVal <= alpha) {
                return bestVal to bestTurn
            }
        }

        //TODO(Make right hashing and then we can use this part)
        /*if (depth < maxDepth / 2) {
            turnsByHash[model.hashCode()] = bestTurn
        }*/
        return bestVal to bestTurn
    }

    override fun calcValue(model: CheckersModel): Int {
        val pe = PositionEvaluation(model, model.whoMoves)
        var value = 0
        for (i in 0 until vector.size) {
            value += pe.params[i] * vector[i]
        }
        return value
    }

    /**
     * Method for sorting turns list for more quickly working of AlphaBeta algorithm
     * @param turns MutableList<BaseTurn>
     */
    override fun sortTurns(turns: MutableList<BaseTurn>) {
        turns.shuffle(Random())
    }
}
