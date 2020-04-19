import java.lang.Exception

open class MinimaxPlayer(name: String, color: Color) : BasePlayer(color) {

    var recCount = 0

    override fun makeTurn(model: BaseModel): BaseTurn {
        if ((model as CheckersModel).possibleTurns().size == 1) {
            return (model as CheckersModel).possibleTurns()[0]
        }
        val tmpModel = CheckersModel(model as CheckersModel)
        recCount = 0
        val turn = minimaxRecursive(tmpModel, 0, 5).second!!
        println(recCount)
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

    //Function that performs the necessary moves
    private fun minimaxRecursive(model : CheckersModel, depth: Int, maxDepth: Int) : Pair<Double, BaseTurn?> {
        recCount++
        val isTerminalStateResult = isTerminalState(model, depth, maxDepth)
        if (isTerminalStateResult != null) {
            return isTerminalStateResult to null
        }
        //Maybe, we already had this position in not so much depth, then we can use early result
        val turns =/* if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!)
        else*/
            model.possibleTurns()

        var bestVal = -(Double.MAX_VALUE / 10)
        var bestTurn = turns[0]
        for (turn in turns) {
            val retainer = ModelRetainer(model, turn)
            try {
                model.move(turn)
            } catch (e : Exception) {
                println(turn.toString())
                model.board.print()
            }
            val ans = minimaxRecursive(model, depth + if (model.whoMoves == retainer.whoMoves) 0 else 1, maxDepth).first *
                    (if (model.whoMoves == retainer.whoMoves) 1 else -1)
            retainer.reset(model)
            if (depth == 0) {
                println(turn.toString() + " : " + ans)
            }
            if (ans > bestVal) {
                bestVal = ans
                bestTurn = turn
            }
        }
        if (depth < maxDepth / 2) {
            turnsByHash[model.hashCode()] = bestTurn
        }
        return bestVal to bestTurn
    }

    protected fun isTerminalState(model: CheckersModel, depth: Int, maxDepth: Int): Double? {
        if (model.gameState != GameState.PLAYING) {
            var value = Double.MAX_VALUE / 5 + (100.0 - depth)
            if (model.gameState.getColor() != model.whoMoves) {
                value *= -1.0
            }
            return value
        }
        if (depth >= maxDepth && (!model.canEat() || depth >= maxDepth * 5)) {
            return calcValue(model)
        }
        return null
    }

    protected open fun calcValue(model: CheckersModel): Double {
        val currentNum = model.board.countCheckers(model.whoMoves)
        val anotherNum = model.board.countCheckers(model.whoMoves.nextColor())
        val currentQueenNum = model.board.countQueenCheckers(model.whoMoves)
        val anotherQueenNum = model.board.countQueenCheckers(model.whoMoves.nextColor())
        return (currentQueenNum - anotherQueenNum).toDouble() * 30.0 +
                (currentNum - anotherNum).toDouble() * 3.0
    }


}