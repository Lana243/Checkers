import java.lang.Exception

open class MinimaxAlphaBetaPlayer(name: String, color: Color) : MinimaxPlayer(name, color) {
    override fun makeTurn(model: BaseModel): BaseTurn {
        val tmpModel = CheckersModel(model as CheckersModel)
        recCount = 0
        val turn = minimaxAlphaBetaRecursive(tmpModel, 0, 6).second!!
        println(recCount)
        return turn
    }

    private fun minimaxAlphaBetaRecursive(model: CheckersModel, depth: Int, maxDepth: Int, alpha: Double = -(1e100), beta: Double = (1e100), changeColor: Boolean = false): Pair<Double, BaseTurn?> {
        recCount++
        val isTerminalStateResult = isTerminalState(model, depth, maxDepth)
        if (isTerminalStateResult != null) {
            return isTerminalStateResult to null
        }
        //Maybe, we already had this position in not so much depth, then we can use early result
        val turns = /*if (turnsByHash[model.hashCode()] != null)
            listOf(turnsByHash[model.hashCode()]!!).toMutableList()
        else*/
            model.possibleTurns().toMutableList()
        sortTurns(turns)
        var bestVal = -(1e100)
        var bestTurn = turns[0]
        for (turn in turns) {
            val retainer = ModelRetainer(model, turn)
            try {
                model.move(turn)
            } catch (e : Exception) {
                println(turn.toString())
                model.board.print()
            }
            var ans = minimaxAlphaBetaRecursive(model,
                    depth + if (model.whoMoves == retainer.whoMoves) 0 else 1,
                    maxDepth,
                    if (model.whoMoves == retainer.whoMoves) alpha else bestVal,
                    if (model.whoMoves == retainer.whoMoves) beta else alpha,
                    model.whoMoves == retainer.whoMoves).first * (if (model.whoMoves == retainer.whoMoves) 1 else -1)
            ans *= 100
            ans += calcValue(model)
            retainer.reset(model)
            if (depth == 0) {
                println(turn.toString() + " : " + ans)
            }
            if (ans > bestVal) {
                bestVal = ans
                bestTurn = turn
            }
            if (changeColor && (-bestVal - alpha <= 0.01 || bestVal - beta >= 0.01)) {
                return bestVal to bestTurn
            }
        }
        if (depth < maxDepth / 2) {
            turnsByHash[model.hashCode()] = bestTurn
        }
        return bestVal to bestTurn
    }

    //Method for sorting turns list for more quickly working of AlphaBeta algorithm
    protected open fun sortTurns(turns: MutableList<BaseTurn>) {
        turns.shuffle()
    }
}
