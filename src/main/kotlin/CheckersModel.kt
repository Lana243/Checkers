import kotlin.math.abs

class CheckersModel() : BaseModel(8) {
    private var whoMoves = Color.WHITE

    override fun canMove(turn: BaseTurn): Pair<Boolean, Any?> {
        if (turn.playerColor != whoMoves) {
            //ходит не тот, кто должен бы
            return Pair(false, null)
        }
        //TODO("Проверка выхода за границы")
        val squareFrom = board[turn.from.first][turn.from.second]
        val squareTo = board[turn.to.first][turn.to.second]
        if (!squareFrom.color || !squareTo.color) {
            return Pair(false, null)
        }
        if (squareFrom.figure != null) {
            if (squareTo.figure != null) {
                //клетка "куда" не пустая
                return Pair(false, null)
            }
            if (squareFrom.figure!!.color != turn.playerColor) {
                //фигура на клетку "откуда" не совпадает с цветом ходящего
                return Pair(false, null)
            }
            val verticals = turn.to.second - turn.from.second
            val horizontals = turn.to.first - turn.from.first
            if ((horizontals == if (turn.playerColor == Color.WHITE) 1 else -1) && (abs(verticals) == 1)) {
                return Pair(true, null)
            }
            if ((abs(horizontals) == 2) && (abs(verticals) == 2)
                    && (board[turn.from.first][turn.from.second].figure!!.type == FigureType.Ordinary)) { //Если просто съедаем через клетку
                val squareToEat = board[(turn.from.first + turn.to.first) / 2][(turn.from.second + turn.to.second) / 2]
                if ((squareToEat.figure != null) && (squareToEat.figure?.color != squareFrom.figure!!.color)) { //проверяем что там стоит шашка другого цвета
                    return Pair(true, squareToEat)
                }
            }
            /*if ((abs(horizontals) == abs(verticals)) && (squareFrom.figure!!.type == FigureType.Queen)) { //съедаем через несколько клеток(должна быть дамкой)
                var count: Int = 0
                for (i in 1 until abs(horizontals) + 1) {

                }
            }*/
            return Pair(false, null)
        } else {
            //клетка "откуда" пустая
            return Pair(false, null)
        }
    }

    private fun makeQueen(turn: BaseTurn) {
        if (turn.to.first == 0 && turn.playerColor == Color.BLACK ||
            turn.to.first == boardSize - 1 && turn.playerColor == Color.WHITE)
            board[turn.to.first][turn.to.second].figure?.type ?: FigureType.Queen
    }

    override fun move(turn: BaseTurn) {
        val canMoveResult = canMove(turn)
        if (!canMove(turn).first)
            return
        run {
            board[turn.to.first][turn.to.second].figure = board[turn.from.first][turn.from.second].figure
            board[turn.from.first][turn.from.second].figure = null
            whoMoves = if (whoMoves == Color.WHITE)
                Color.BLACK
            else
                Color.WHITE
            updateState()
        }
        if (canMoveResult.second != null) {
            (canMoveResult.second as Square).figure = null
        }

        //TODO("Сделать съедание шашек")
        makeQueen(turn)
    }

    override fun updateState() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun printBoardOnConsole() {
        for (i in boardSize - 1 downTo 0) {
            for (j in 0 until boardSize) {
                var t = "."
                if (board[i][j].figure == null) {
                    t = "."
                } else {
                    t = if (board[i][j].figure!!.color == Color.WHITE) {
                        "w"
                    } else {
                        "b"
                    }
                    if (board[i][j].figure!!.type == FigureType.Queen) {
                        t.toUpperCase()
                    }
                }
                print(t)
            }
            println()
        }
    }

    init {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if ((i + j) % 2 == 0) {
                    board[i][j].color = true
                    if (i <= 2)
                        board[i][j].figure = Figure("w")
                    if (i >= 5)
                        board[i][j].figure = Figure("b")
                } else {
                    board[i][j].color = false
                }
            }
        }
    }
}