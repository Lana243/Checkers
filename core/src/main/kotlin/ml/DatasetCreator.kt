package ml

import core.BaseTurn
import core.CheckersModel
import core.Color
import java.io.File

class DatasetCreator {

    val TestNumber = 3

    fun findPath(m : CheckersModel, turn : BaseTurn, used : BooleanArray) : ArrayList<Pair<Int, Int>>? {
        if (turn.to.first == turn.from.first && turn.to.second == turn.from.second) {
            return ArrayList<Pair<Int, Int>>()
        }
        used[turn.from.first * m.board. boardSize + turn.from.second] = true
        var dx = 1
        for (d in -m.board.boardSize..m.board.boardSize)
            for (i in 0..1) {
                val newTurn = BaseTurn(turn.playerColor, turn.from.first + d * dx to turn.from.second + d, turn.to)
                if (m.canMove(BaseTurn(turn.playerColor, turn.from, newTurn.from)) != null) {
                    if (used[newTurn.from.first * m.board.boardSize + newTurn.from.second])
                        continue
                    val tmpModel = CheckersModel(m)
                    tmpModel.move(BaseTurn(turn.playerColor, turn.from, newTurn.from))
                    val path = findPath(tmpModel, newTurn, used)
                    if (path != null) {
                        path.add(newTurn.from)
                        return path
                    }
                }
                dx *= -1
            }
        return null
    }

    fun smartMove(m : CheckersModel, turn : BaseTurn) : Boolean {
        val used = BooleanArray(m.board.boardSize * m.board.boardSize)
        val path = findPath(m, turn, used) ?: return false
        var cur = turn.from
        for (next in path.reversed()) {
            m.move(BaseTurn(turn.playerColor, cur, next))
            cur = next
        }
        return true
    }

    fun readGame(scan: java.util.Scanner) : ArrayList<PositionEvaluation>? {
        val m = CheckersModel()
        val result = scan.nextInt()
        val ans = ArrayList<PositionEvaluation>()
        while (true) {
            val fromX = scan.nextInt()
            val fromY = scan.nextInt()
            val toX = scan.nextInt()
            val toY = scan.nextInt()
            if (fromX == 0 && fromY == 0 && toX == 0 && toY == 0)
                break
            if (!smartMove(m, BaseTurn(m.whoMoves, fromX to fromY, toX to toY)))
                return null
            ans.add(PositionEvaluation(m, m.whoMoves, if (m.whoMoves == Color.WHITE) result else -result))
        }
        return ans
    }

    fun createDataset() {
        val inputFile = File("core/src/main/assets/datasets/games.txt")
        val outputFile = File("core/src/main/assets/datasets/train.csv").bufferedWriter()
        val scan = java.util.Scanner(inputFile)
        outputFile.write("My checkers quantity," + "Adversary checkers quantity," +
                        "My queens quantity," + "Adversary queens quantity," +
                        "My average horizontal," + "Adversary average horizontal," +
                        "My average vertical," + "Adversary average vertical," +
                        "My queen on main diagonal" + "Adversary queen on main diagonal" +
                        "Evaluation\n")
        while (scan.hasNextLine()) {
            val list = readGame(scan)
            if (list != null) {
                for (i in list.size / 3 until list.size) {
                    val e = list[i]
                    outputFile.write("${e.checkersQuantity.first}," + "${e.checkersQuantity.second}," +
                                    "${e.queenQuantity.first}," + "${e.queenQuantity.second}," +
                                    "${e.averageHorizontal.first}," + "${e.averageHorizontal.second}," +
                                    "${e.averageVertical.first}," + "${e.averageVertical.second}," +
                                    "${e.mainDiagQueen.first}," + "${e.mainDiagQueen.second}," +
                                    "${e.eval}\n")
                }
            }
        }
        outputFile.close()
    }
}