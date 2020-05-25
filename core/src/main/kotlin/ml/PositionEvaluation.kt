package ml

import core.CheckersModel
import core.Color
import core.FigureType

class PositionEvaluation(val model: CheckersModel, val color: Color, val eval: Int) {
    private val params = calc()

    val checkers = Pair(params[0], params[1])
    val queens = Pair(params[2], params[3])
    val mainDiagQueen = Pair(params[10], params[11])
    val averageVertical = Pair(if (checkers.first != 0) 1 + params[6] / (1.0 * checkers.first) else 0,
                                if (checkers.second != 0) 1 + params[7] / (1.0 * checkers.second) else 0)
    val averageHorizontal = Pair(if (checkers.first != 0) 1 + params[8] / (1.0 * checkers.first) else 0,
                                if (checkers.second != 0) 1 + params[9] / (1.0 * checkers.second) else 0)
    val safeCheckers = Pair(params[4], params[5])
    val twoDiag = Pair(params[12], params[13])
    val threeDiag = Pair(params[14], params[15])
    val triangles = Pair(params[16], params[17])
    val flippedTriangles = Pair(params[18], params[19])

    val c5_f4 = calcSpecialPositions(4, 2)
    val e5_d4 = calcSpecialPositions(4, 4)
    val a7_h2 = calcSpecialPositions(6, 0)
    val a3_h6 = calcSpecialPositions(2, 0)
    val h4_a5 = calcSpecialPositions(3, 7)

    private fun calc() : IntArray {
        val ans = IntArray(20, {0})
        /**
         * ans[2 * i] for color player, ans[2 * i + 1] for adversary
         * ans[0] - number of checkers
         * ans[2] - number of queens
         * ans[4] - number of safe checkers
         * ans[6] - sum of verticals
         * ans[8] - sum of horizontals
         * ans[10] - has queen on main diagonal
         * ans[12] - number of figures: two neighboring checkers on diagonal
         * ans[14] - number of figures: three neighboring checkers on diagonal
         * ans[16] - number of /\ triangles
         * ans[18] - number of \/ triangles
         */
        for (i in 0 until model.board.boardSize)
            for (j in 0 until model.board.boardSize) {
                if (model.board[i, j].figure == null)
                    continue
                val r = if (model.board[i, j].figure?.color == color) 0 else 1
                ans[r]++
                if (model.board[i, j].figure?.type == FigureType.Queen) {
                    ans[2 + r]++
                    if (i == j)
                        ans[10 + r] = 1
                }
                if (safeChecker(i, j))
                    ans[4 + r]++
                ans[6 + r] += j
                ans[8 + r] += i
                val neighbouringCheckers = countNeighbouringCheckers(i, j)
                if (neighbouringCheckers >= 2)
                    ans[12 + r]++
                if (neighbouringCheckers >= 3)
                    ans[14 + r]++
                val triangles = calcTriangles(i, j, 1) to calcTriangles(i, j, -1)
                if (model.board[i, j].figure?.color == Color.WHITE) {
                    ans[16 + r] += triangles.first
                    ans[18 + r] += triangles.second
                } else {
                    ans[16 + r] += triangles.second
                    ans[18 + r] += triangles.first
                }
            }
        return ans
    }

    private fun safeChecker(i: Int, j: Int) : Boolean {
        for (ti in i - 1..i + 1 step 2)
            for (tj in j - 1..j + 1 step 2) {
                   if (model.board.isValidCoords(ti, tj) && model.board[ti, tj].figure != null)
                       return false
            }
        return true
    }

    private fun countNeighbouringCheckers(i: Int, j: Int) : Int {
        var max = 0
        for (dj in -1..1 step 2) {
            var ans = 0
            var ti = i
            var tj = j
            while (model.board.isValidCoords(ti, tj) && model.board[i, j].figure?.color == model.board[ti, tj].figure?.color) {
                ans++
                ti++
                tj += dj
            }
            max = kotlin.math.max(max, ans)
            if (max == 3)
                break
        }
        return max
    }

    private fun calcTriangles(i: Int, j: Int, di: Int) : Int {
        if (!model.board.isValidCoords(i, j + 2) || !model.board.isValidCoords(i + di, j + 1))
            return 0
        return if (model.board[i, j].figure?.color == model.board[i, j + 2].figure?.color &&
                model.board[i, j].figure?.color == model.board[i + di, j + 1].figure?.color) 1 else 0
    }

    private fun calcSpecialPositions(iWhite: Int, jWhite: Int) : Pair<Int, Int> {
        val iBlack = model.board.boardSize - 1 - iWhite
        val jBlack = model.board.boardSize - 1 - jWhite
        val white = if (model.board[iWhite, jWhite].figure?.color == Color.WHITE) 1 else 0
        val black = if (model.board[iBlack, jBlack].figure?.color == Color.BLACK) 1 else 0
        if (color == Color.WHITE) {
            return Pair(white, black)
        }
        return Pair(black, white)
    }
}