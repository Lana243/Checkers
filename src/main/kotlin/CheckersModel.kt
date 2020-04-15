import kotlin.math.abs

class CheckersModel : BaseModel() {
    val board = CheckersBoard(8)
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
        if (!turn.isValid(board.boardSize)) {
            return null
        }

        val (squareFrom, squareTo) = board[turn]
        if (squareFrom.color == 1 || squareTo.color == 1) {
            return null
        }
        val squareFromFigure = squareFrom.figure
        if (squareFromFigure != null) {
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
            if (horizontals == turn.playerColor.getDirection() && abs(verticals) == 1) {
                //move without eating
                return squareFrom
            }
            //if ordinary checker make "eaten" move
            if (abs(horizontals) == 2 && abs(verticals) == 2
                    && squareFromFigure.type == FigureType.Ordinary) {
                val squareToEat = board[(turn.from.first + turn.to.first) / 2, (turn.from.second + turn.to.second) / 2]
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
        if (turn.to.first == turn.playerColor.queenLine(board.boardSize))
            board[turn.to].figure!!.type = FigureType.Queen
    }

    override fun move(turn: BaseTurn)  {
        val canMoveResult = canMove(turn) ?: throw IllegalStateException(
                "Turn isn't valid now, but model is trying to apply it.")
        board[turn.to].figure = board[turn.from].figure
        board[turn.from].figure = null
        whoMoves = whoMoves.nextColor()
        updateState()

        canMoveResult.figure = null
        //TODO("Make Queen eating")
        makeQueen(turn)
    }

    override fun updateState() {
        //TODO("not implemented")
    }

}