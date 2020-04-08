import kotlin.math.abs

class CheckersModel() : BaseModel(8) {
    init {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if ((i + j) % 2 == 0) {
                    board[i][j].color = -1
                    if (i <= 2)
                        board[i][j].figure = Figure("w")
                    if (i >= 5)
                        board[i][j].figure = Figure("b")
                } else {
                    board[i][j].color = 1
                }
            }
        }
    }

    private var whoMoves = 1

    /* This functions checking turn for legacy and rules and also find checker, that will be eaten.
    Its return value has 3 options:
        if turn is illegal, it returns null,
        if turn is legal but there aren't any checker will be eaten,
            it returns square, from which the turn started
        if turn is legal and some checker must be eaten,
            it return square, where this checker is located */
    override fun canMove(turn: BaseTurn): Square? {
        if (turn.playerColor != whoMoves) {
            //player's color isn't correct
            return null
        }
        if (turn.to.first < 0 || turn.to.first >= boardSize || turn.to.second < 0 || turn.to.second >= boardSize)
            return null
        if (turn.from.first < 0 || turn.from.first >= boardSize || turn.from.second < 0 || turn.from.second >= boardSize)
            return null
        //TODO("Checking than move coordinates are not out of board")
        val squareFrom = board[turn.from.first][turn.from.second]
        val squareTo = board[turn.to.first][turn.to.second]
        if (squareFrom.color == 1 || squareTo.color == 1) {
            return null
        }
        if (squareFrom.figure != null) {
            val squareFromFigure : Figure = squareFrom.figure!!
            if (squareTo.figure != null) {
                //square to is not empty - can't move there
                return null
            }
            if (squareFromFigure.color != turn.playerColor) {
                //figure's color on "from" square isn't equal to player's color - illegal move
                return null
            }
            val verticals = turn.to.second - turn.from.second
            val horizontals = turn.to.first - turn.from.first
            if ((horizontals == if (turn.playerColor == 1) 1 else -1) && (abs(verticals) == 1)) {
                //move without eating
                return squareFrom
            }
            //if ordinary checker make "eaten" move
            if ((abs(horizontals) == 2) && (abs(verticals) == 2)
                    && (squareFromFigure.type == FigureType.Ordinary)) {
                val squareToEat = board[(turn.from.first + turn.to.first) / 2][(turn.from.second + turn.to.second) / 2]
                //checking, that there is another color's checker on "eaten" square
                if ((squareToEat.figure != null) && (squareToEat.figure?.color != squareFromFigure.color)) {
                    return squareToEat
                }
            }
            /*if ((abs(horizontals) == abs(verticals)) && (squareFrom.figure!!.type == FigureType.Queen)) { //съедаем через несколько клеток(должна быть дамкой)
                var count: Int = 0
                for (i in 1 until abs(horizontals) + 1) {

                }
            }*/
            return null
        } else {
            //square "from" is empty
            return null
        }
    }

    private fun makeQueen(turn: BaseTurn) {
        if ((turn.to.first == 0 && turn.playerColor == -1) ||
            (turn.to.first == boardSize - 1 && turn.playerColor == 1))
            board[turn.to.first][turn.to.second].figure!!.type = FigureType.Queen

    }

    override fun move(turn: BaseTurn) {
        val canMoveResult = canMove(turn) ?: return
        board[turn.to.first][turn.to.second].figure = board[turn.from.first][turn.from.second].figure
        board[turn.from.first][turn.from.second].figure = null
        whoMoves *= -1
        updateState()

        canMoveResult.figure = null
        //TODO("Make Queen eating")
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
                    t = if (board[i][j].figure!!.color == 1) {
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