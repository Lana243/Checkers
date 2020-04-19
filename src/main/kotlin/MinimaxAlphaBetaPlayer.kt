import java.lang.Exception

class MinimaxAlphaBetaPlayer(name: String, color: Color) : MinimaxPlayer(name, color) {
    override fun makeTurn(model: BaseModel): BaseTurn {
        val tmpModel = CheckersModel(model as CheckersModel)
        val turn = minimaxAlphaBetaRecursive(tmpModel, 0, 6).second!!
        return turn
    }
    private fun minimaxAlphaBetaRecursive(model: CheckersModel, depth: Int, maxDepth: Int, tmpBest: Double = Double.MIN_VALUE, changeColor: Boolean = false): Pair<Double, BaseTurn?> {
        val isTerminalStateResult = isTerminalState(model, depth, maxDepth)
        if (isTerminalStateResult != null) {
            return isTerminalStateResult to null
        }
        //Maybe, we already had this position in not so much depth, then we can use early result
        val turns = if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!)
        else
            model.possibleTurns()

        var bestVal = -100000.0
        var bestTurn = turns[0]
        for (turn in turns) {
            val retainer = ModelRetainer(model, turn)
            try {
                model.move(turn)
            } catch (e : Exception) {
                println(turn.toString())
                model.board.print()
            }
            val ans = minimaxAlphaBetaRecursive(model,
                    depth + if (model.whoMoves == retainer.whoMoves) 0 else 1,
                    maxDepth,
                    if (model.whoMoves == retainer.whoMoves) tmpBest else bestVal,
                    model.whoMoves == retainer.whoMoves).first * (if (model.whoMoves == retainer.whoMoves) 1 else -1)
            retainer.reset(model)
            if (ans > bestVal) {
                bestVal = ans
                bestTurn = turn
            }
            if (changeColor && -bestVal - tmpBest <= 0.01) {
                return bestVal to bestTurn
            }
        }
        if (depth < maxDepth / 2) {
            turnsByHash[model.hashCode()] = bestTurn
        }
        return bestVal to bestTurn
    }
}
