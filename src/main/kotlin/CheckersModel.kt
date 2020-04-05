import kotlin.math.abs

class CheckersModel() : BaseModel(8) {
    private var whoMoves = Color.WHITE

    override fun canMove(turn: BaseTurn): Boolean {
        if (turn.playerColor != whoMoves) {
            //ходит не тот, кто должен бы
            return false
        }
        //TODO("Проверка выхода за границы")
        val squareFrom = board[turn.from.first][turn.from.second]
        val squareTo = board[turn.to.first][turn.to.second]
        if (!squareFrom.color || !squareTo.color) {
            return false
        }
        if (squareFrom.figure != null) {
            if (squareTo.figure != null) {
                //клетка "куда" не пустая
                return false
            }
            if (squareFrom.figure!!.color != turn.playerColor) {
                //фигура на клетку "откуда" не совпадает с цветом ходящего
                return false;
            }
            val verticals = turn.to.second - turn.from.second
            val horizontals = turn.to.first - turn.from.first
            if ((horizontals == if (turn.playerColor == Color.WHITE) 1 else -1) && (abs(verticals) == 1)) {
                //Просто ход без съедания
                return true
            }
            //TODO("Проверка хода со съеданием")
            return false
        } else {
            //клетка "откуда" пустая
            return false
        }
    }

    override fun move(turn: BaseTurn) {
        if (!canMove(turn))
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
        //TODO("Сделать съедание шашек")
        //TODO("Сделать превращение в дамку")
    }

    override fun updateState() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun printBoardOnConsole() {
        for (i in boardSize-1 downTo 0) {
            for (j in 0 until boardSize) {
                var t = "."
                if (board[i][j].figure == null) {
                    t = "."
                } else {
                    if (board[i][j].figure!!.color == Color.WHITE) {
                        t = "w"
                    } else {
                        t = "b"
                    }
                    if (board[i][j].figure!!.type == FigureType.Queen) {
                        t.toUpperCase()
                    }
                }
                print(t)
            }
            println();
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