import java.lang.Exception

class MinimaxPlayer(name: String, color: Color) : BasePlayer(color) {

    override fun makeTurn(model: BaseModel): BaseTurn {
        val tmpModel = CheckersModel(model as CheckersModel)
        return minimaxRecursive(tmpModel, 0, 10).second!!
    }

    val turnsByHash : MutableMap<Int, BaseTurn> = mutableMapOf()

    //Function that performs the necessary moves
    private fun minimaxRecursive(model : CheckersModel, depth: Int, maxDepth: Int) : Pair<Double, BaseTurn?> {
        if (depth == maxDepth) {
            return calcValue(model) to null
        }
        if (model.gameState != GameState.PLAYING) {
            return 10.0 * (if (model.gameState.getColor() == model.whoMoves) 1 else -1) to null
        }

        val turns = model.possibleTurns()
        var bestVal = -10.0
        var bestTurn = turns[0]
        val whoMoves = model.whoMoves
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
            val ans = minimaxRecursive(model, depth+1, maxDepth).first *
                    (if (model.whoMoves == whoMoves) 1 else -1)
            squareEaten?.let {
                it.figure = eatenFigure
            }
            squareFrom.figure = squareFromFigure
            squareTo.figure = squareToFigure
            model.whoMoves = whoMoves
            model.gameState = GameState.PLAYING
            if (ans > bestVal) {
                bestVal = ans
                bestTurn = turn
            }
        }
        return bestVal to bestTurn
    }

    private fun calcValue(model: CheckersModel): Double {
        val currentNum = model.board.getCoords(model.whoMoves).size
        val anotherNum = model.board.getCoords(model.whoMoves.nextColor()).size
        return (currentNum - anotherNum).toDouble() / 12 * 10
    }


}
