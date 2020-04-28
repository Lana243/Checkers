import java.lang.Exception
import java.lang.IllegalStateException
import java.util.logging.Level
import java.util.logging.Logger

open class MinimaxPlayer(name: String, color: Color, private val maxDepth: Int) : BasePlayer<CheckersModel>(color) {

    companion object {
        const val MIN_VALUE = Int.MIN_VALUE / 5
        const val MAX_VALUE = Int.MAX_VALUE / 5
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    var recCount = 0

    override fun makeTurn(model: CheckersModel): BaseTurn {
        if (model.possibleTurns().size == 1) {
            return model.possibleTurns()[0]
        }
        val tmpModel = CheckersModel(model)
        recCount = 0
        val turn = minimaxRecursive(tmpModel, 0, maxDepth).second ?: throw Exception("Can't find move")
        logger.log(Level.INFO, "Number of recursive calls is $recCount")
        return turn
    }

    protected class ModelRetainer (model: CheckersModel, turn: BaseTurn) {
        private val squareFrom = model.board[turn.from]
        private val squareFromFigure = squareFrom.figure?.copy()
        private val squareTo = model.board[turn.to]
        private val squareToFigure = squareTo.figure?.copy()
        private val squareEaten = model.canMove(turn)
        private val eatenFigure = squareEaten?.figure?.copy()
        val whoMoves = model.whoMoves
        private val eatingChecker = model.eatingChecker

        fun reset(model: CheckersModel) {
            squareEaten?.let {
                it.figure = eatenFigure
            }
            squareFrom.figure = squareFromFigure
            squareTo.figure = squareToFigure
            model.whoMoves = whoMoves
            model.gameState = GameState.PLAYING
            model.eatingChecker = eatingChecker
        }
    }

    protected val turnsByHash : MutableMap<Int, BaseTurn> = mutableMapOf()

    /**
     * Function that performs the necessary moves
     * @param model CheckersModel
     * @param depth Int
     * @param maxDepth Int
     * @return Pair<Int, BaseTurn?>
     */
    private fun minimaxRecursive(model : CheckersModel, depth: Int, maxDepth: Int) : Pair<Int, BaseTurn?> {
        recCount++
        val isTerminalStateResult = isTerminalState(model, depth, maxDepth)
        if (isTerminalStateResult != null) {
            return isTerminalStateResult to null
        }

        // Maybe, we already had this position in not so much depth, then we can use early result
        val turns =/* if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!)
        else*/
            model.possibleTurns()

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
            val ans = minimaxRecursive(model, depth + if (model.whoMoves == retainer.whoMoves) 0 else 1, maxDepth).first *
                    (if (model.whoMoves == retainer.whoMoves) 1 else -1)
            retainer.reset(model)
            if (depth == 0) {
                logger.log(Level.INFO, turn.toString() + " : " + ans)
            }
            if (ans > bestVal) {
                bestVal = ans
                bestTurn = turn
            }
        }

        //TODO(Make right hashing and then we can use this part)
        /*if (depth < maxDepth / 2) {
            turnsByHash[model.hashCode()] = bestTurn
        }*/
        return bestVal to bestTurn
    }

    protected fun isTerminalState(model: CheckersModel, depth: Int, maxDepth: Int): Int? {
        if (model.gameState != GameState.PLAYING) {
            var value = MAX_VALUE - depth
            if (model.gameState.winnerColor() != model.whoMoves) {
                value *= -1
            }
            return value
        }
        if (depth >= maxDepth && (!model.canEat() || depth >= maxDepth * 5)) {
            return calcValue(model)
        }
        return null
    }

    protected open fun calcValue(model: CheckersModel): Int {
        val currentNum = model.board.countCheckers(model.whoMoves)
        val anotherNum = model.board.countCheckers(model.whoMoves.nextColor())
        val currentQueenNum = model.board.countQueenCheckers(model.whoMoves)
        val anotherQueenNum = model.board.countQueenCheckers(model.whoMoves.nextColor())
        return (currentQueenNum - anotherQueenNum) * 30 +
                (currentNum - anotherNum) * 3
    }
}
