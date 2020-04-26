import java.lang.Exception
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.max
import kotlin.math.min

open class MinimaxAlphaBetaPlayer(name: String, color: Color) : MinimaxPlayer(name, color) {

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    override fun makeTurn(model: BaseModel): BaseTurn {
        val tmpModel = CheckersModel(model as CheckersModel)
        recCount = 0
        val turn = minimaxAlphaBetaRecursive(tmpModel, 0, 8).second!!
        logger.log(Level.INFO, "Number of recursive calls is $recCount")
        return turn
    }

    private fun minimaxAlphaBetaRecursive(model: CheckersModel,
                                          depth: Int,
                                          maxDepth: Int,
                                          alpha: Int = -(Int.MAX_VALUE / 10),
                                          beta: Int = -(Int.MAX_VALUE / 10),
                                          changeColor: Boolean = false)
            : Pair<Int, BaseTurn?> {

        recCount++

        val isTerminalStateResult = isTerminalState(model, depth, maxDepth)
        if (isTerminalStateResult != null) {
            return isTerminalStateResult to null
        }
        /**
         * Maybe, we already had this position in not so much depth, then we can use early result
         */
        val turns = /*if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!).toMutableList()
        else*/
            model.possibleTurns().toMutableList()
        sortTurns(turns)
        var bestVal = -(Int.MAX_VALUE / 10)
        var bestTurn = turns[0]
        for (turn in turns) {
            val retainer = ModelRetainer(model, turn)
            try {
                model.move(turn)
            } catch (e : Exception) {
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
            if (depth <= 0) {
                logger.log(Level.INFO, turn.toString() + " : " + ans)
            }
            if (ans > bestVal) {
                bestVal = ans
                bestTurn = turn
            }
            if (changeColor && -bestVal <= alpha) {
                return bestVal to bestTurn
            }
        }
        if (depth < maxDepth / 2) {
            turnsByHash[model.hashCode()] = bestTurn
        }
        return bestVal to bestTurn
    }

    /**
     * Method for sorting turns list for more quickly working of AlphaBeta algorithm
     * @param turns MutableList<BaseTurn>
     */
    protected open fun sortTurns(turns: MutableList<BaseTurn>) {
        turns.shuffle(Random())
    }
}
