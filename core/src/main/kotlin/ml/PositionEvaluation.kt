package ml

import core.CheckersModel
import core.Color
import core.FigureType

class PositionEvaluation(val model: CheckersModel, val color: Color) {
    val params = calc()

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

    lateinit var c5_f4: Pair<Int, Int>
    lateinit var e5_d4: Pair<Int, Int>
    lateinit var a7_h2: Pair<Int, Int>
    lateinit var a3_h6: Pair<Int, Int>
    lateinit var h4_a5: Pair<Int, Int>

    private fun calc() : IntArray {
        val ans = IntArray(30) {0}
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
                val neighbouringCheckers = countNeighbouringCheckers(i, j, 1) to countNeighbouringCheckers(i, j, -1)
                if (neighbouringCheckers.first >= 2)
                    ans[12 + r]++
                if (neighbouringCheckers.second >= 2)
                    ans[12 + r]++
                if (neighbouringCheckers.first >= 3)
                    ans[14 + r]++
                if (neighbouringCheckers.second >= 3)
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
        c5_f4 = calcSpecialPositions(4, 2)
        e5_d4 = calcSpecialPositions(4, 4)
        a7_h2 = calcSpecialPositions(6, 0)
        a3_h6 = calcSpecialPositions(2, 0)
        h4_a5 = calcSpecialPositions(3, 7)
        ans[20] = c5_f4.first; ans[21] = c5_f4.second
        ans[22] = e5_d4.first; ans[23] = e5_d4.second
        ans[24] = a7_h2.first; ans[25] = a7_h2.second
        ans[26] = a3_h6.first; ans[27] = a3_h6.second
        ans[28] = h4_a5.first; ans[29] = h4_a5.second
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

    private fun countNeighbouringCheckers(i: Int, j: Int, dj: Int) : Int {
        var ans = 0
        var ti = i
        var tj = j
        while (model.board.isValidCoords(ti, tj) && model.board[i, j].figure?.color == model.board[ti, tj].figure?.color) {
            ans++
            ti++
            tj += dj
        }
        return ans
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