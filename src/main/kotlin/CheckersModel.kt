import kotlin.math.abs

class CheckersModel() : BaseModel(8) {
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

    private var whoMoves = Color.WHITE

    override fun canMove(turn: BaseTurn): Pair<Boolean, Any?> {
        if (turn.playerColor != whoMoves) {
            //player's color isn't correct
            return Pair(false, null)
        }
        //TODO("Checking than move coordinates are not out of board")
        val squareFrom = board[turn.from.first][turn.from.second]
        val squareTo = board[turn.to.first][turn.to.second]
        if (!squareFrom.color || !squareTo.color) {
            return Pair(false, null)
        }
        if (squareFrom.figure != null) {
            val squareFromFigure : Figure = squareFrom.figure!!
            if (squareTo.figure != null) {
                //square to is not empty - can't move there
                return Pair(false, null)
            }
            if (squareFromFigure.color != turn.playerColor) {
                //figure's color on "from" square isn't equal to player's color - illegal move
                return Pair(false, null)
            }
            val verticals = turn.to.second - turn.from.second
            val horizontals = turn.to.first - turn.from.first
            if ((horizontals == if (turn.playerColor == Color.WHITE) 1 else -1) && (abs(verticals) == 1)) {
                return Pair(true, null)
            }
            //if ordinary checker make "eaten" move
            if ((abs(horizontals) == 2) && (abs(verticals) == 2)
                    && (squareFromFigure.type == FigureType.Ordinary)) {
                val squareToEat = board[(turn.from.first + turn.to.first) / 2][(turn.from.second + turn.to.second) / 2]
                //checking, that there is another color's checker on "eaten" square
                if ((squareToEat.figure != null) && (squareToEat.figure?.color != squareFromFigure.color)) {
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
            //square "from" is empty
            return Pair(false, null)
        }
    }

    private fun makeQueen(turn: BaseTurn) {
        if ((turn.to.first == 0 && turn.playerColor == Color.BLACK) ||
            (turn.to.first == boardSize - 1 && turn.playerColor == Color.WHITE))
            board[turn.to.first][turn.to.second].figure!!.type = FigureType.Queen

    }

    override fun move(turn: BaseTurn) {
        val canMoveResult = canMove(turn)
        if (!canMove(turn).first)
            return
        board[turn.to.first][turn.to.second].figure = board[turn.from.first][turn.from.second].figure
        board[turn.from.first][turn.from.second].figure = null
        whoMoves = if (whoMoves == Color.WHITE)
            Color.BLACK
        else
            Color.WHITE
        updateState()
        if (canMoveResult.second != null) {
            (canMoveResult.second as Square).figure = null
        }

        //TODO("Make checker eating")
        makeQueen(turn)
    }

    override fun updateState() {
        //TODO("not implemented")
    }

    override fun printBoardOnConsole() {
        for (i in boardSize - 1 downTo 0) {
            for (j in 0 until boardSize) {
                var t : String
                if (board[i][j].figure == null) {
                    t = "."
                } else {
                    t = if (board[i][j].figure!!.color == Color.WHITE) {
                        "w"
                    } else {
                        "b"
                    }
                    if (board[i][j].figure!!.type == FigureType.Queen) {
                        t = t.toUpperCase()
                    }
                }
                print(t)
            }
            println()
        }
        println()
    }
}