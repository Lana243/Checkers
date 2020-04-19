import java.lang.Exception

open class MinimaxPlayer(name: String, color: Color) : BasePlayer(color) {

    override fun makeTurn(model: BaseModel): BaseTurn {
        val tmpModel = CheckersModel(model as CheckersModel)
        val turn = minimaxRecursive(tmpModel, 0, 5).second!!
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
        val isTerminalStateResult = isTerminalState(model, depth, maxDepth)
        if (isTerminalStateResult != null) {
            return isTerminalStateResult to null
        }
        //Maybe, we already had this position in not so much depth, then we can use early result
        val turns = if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!)
        else
            model.possibleTurns()

        var bestVal = Double.MIN_VALUE / 10
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
            return if (model.gameState.getColor() == model.whoMoves) Double.MAX_VALUE / 5 else Double.MIN_VALUE / 5
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
        return (currentQueenNum - anotherQueenNum).toDouble() * 3.3 +
                (currentNum - anotherNum).toDouble() * 0.8
    }


}
