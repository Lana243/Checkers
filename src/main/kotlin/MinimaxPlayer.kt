import java.lang.Exception

class MinimaxPlayer(name: String, color: Color) : BasePlayer(color) {

    override fun makeTurn(model: BaseModel): BaseTurn {
        val tmpModel = CheckersModel(model as CheckersModel)
        val turn = minimaxRecursive(tmpModel, 0, 5).second!!
        return turn
    }

    private val turnsByHash : MutableMap<Int, BaseTurn> = mutableMapOf()

    //Function that performs the necessary moves
    private fun minimaxRecursive(model : CheckersModel, depth: Int, maxDepth: Int) : Pair<Double, BaseTurn?> {
        if (model.gameState != GameState.PLAYING) {
            return 100.0 * (if (model.gameState.getColor() == model.whoMoves) 1 else -1) to null
        }
        if (depth >= maxDepth && (!model.canEat() || depth >= maxDepth * 5)) {
            return calcValue(model) to null
        }
        //Maybe, we already had this position in not so much depth, then we can use early result
        val turns = if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!)
        else
            model.possibleTurns()

        var bestVal = -100000.0
        var bestTurn = turns[0]
        val whoMoves = model.whoMoves
        val eatingChecker = model.eatingChecker
        for (turn in turns) {
            val squareFrom = model.board[turn.from]
            val squareFromFigure = squareFrom.figure?.copy()
            val squareTo = model.board[turn.to]
            val squareToFigure = squareTo.figure?.copy()
            val squareEaten = model.canMove(turn)
            val eatenFigure = squareEaten?.figure?.copy()
            try {
                model.move(turn)
            } catch (e : Exception) {
                println(turn.toString())
                model.board.print()
            }
            val ans = minimaxRecursive(model, depth + if (model.whoMoves == whoMoves) 0 else 1, maxDepth).first *
                    (if (model.whoMoves == whoMoves) 1 else -1)
            squareEaten?.let {
                it.figure = eatenFigure
            }
            squareFrom.figure = squareFromFigure
            squareTo.figure = squareToFigure
            model.whoMoves = whoMoves
            model.gameState = GameState.PLAYING
            model.eatingChecker = eatingChecker
            if (depth <= 2) {
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

    private fun calcValue(model: CheckersModel): Double {
        val currentNum = model.board.countCheckers(model.whoMoves)
        val anotherNum = model.board.countCheckers(model.whoMoves.nextColor())
        val currentQueenNum = model.board.countQueenCheckers(model.whoMoves)
        val anotherQueenNum = model.board.countQueenCheckers(model.whoMoves.nextColor())
        return (currentQueenNum - anotherQueenNum).toDouble() * 3.3 +
                (currentNum - anotherNum).toDouble() * 0.8
    }


}
