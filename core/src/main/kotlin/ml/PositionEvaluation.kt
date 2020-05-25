package ml

import core.CheckersModel
import core.Color
import core.FigureType

class PositionEvaluation(val model: CheckersModel, val color: Color, val eval: Int) {
    val checkersQuantity = calcCheckers()
    val queenQuantity = calcQueens()
    val mainDiagQueen = findQueen()

    private val sum = calcAverage()
    val averageVertical = Pair(if (checkersQuantity.first != 0) sum[1] / (1.0 * checkersQuantity.first) else -1,
                                if (checkersQuantity.second != 0) sum[3] / (1.0 * checkersQuantity.second) else -1)
    val averageHorizontal = Pair(if (checkersQuantity.first != 0) sum[0] / (1.0 * checkersQuantity.first) else -1,
                                if (checkersQuantity.second != 0) sum[2] / (1.0 * checkersQuantity.second) else -1)

    private fun calcCheckers() : Pair<Int, Int> {
        return Pair(model.board.countCheckers(color), model.board.countCheckers(color.nextColor()))
    }

    private fun calcQueens() : Pair<Int, Int> {
        return Pair(model.board.countQueenCheckers(color), model.board.countQueenCheckers(color.nextColor()))
    }

    private fun findQueen() : Pair<Int, Int> {
        var myQueen = 0
        var advQueen = 0
        for (i in 0 until model.board.boardSize) {
            if (model.board[i, i].figure?.type == FigureType.Queen) {
                if (model.board[i, i].figure?.color == color)
                    myQueen = 1
                else
                    advQueen = 1
            }
            if (model.board[i, model.board.boardSize - 1 - i].figure?.type == FigureType.Queen) {
                if (model.board[i, model.board.boardSize - 1 - i].figure?.color == color)
                    myQueen = 1
                else
                    advQueen = 1
            }
        }
        return Pair(myQueen, advQueen)
    }

    private fun calcAverage() : IntArray {
        val sum = IntArray(4, {0})
        for (i in 0 until model.board.boardSize)
            for (j in 0 until model.board.boardSize) {
                if (model.board[i, j].figure == null)
                    continue
                if (model.board[i, j].figure?.color == color) {
                    sum[0] += i
                    sum[1] += j
                } else {
                    sum[2] += i
                    sum[3] += j
                }
            }
        return sum
    }
}