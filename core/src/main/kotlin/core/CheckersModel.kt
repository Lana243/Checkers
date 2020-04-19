package core

import kotlin.math.abs
import kotlin.math.sign

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
            if (abs(verticals) != abs(horizontals)) {
                //Move is not diagonal
                return null
            }
            var numBetween = 0
            var squareBetween = squareFrom
            for (d in 1 until abs(verticals)) {
                val x = turn.from.first + horizontals.sign * d
                val y = turn.from.second + verticals.sign * d
                if (board[x, y].figure != null) {
                    if (board[x, y].figure?.color == whoMoves) {
                        //The checker between 'from' and 'to' squares has same color with moving player
                        return null
                    } else {
                        numBetween++
                        squareBetween = board[x, y]
                    }
                }
            }
            if (numBetween > 1) {
                //More then 1 checker between 'from' and 'to' squares
                return null
            }
            if (squareFromFigure.type == FigureType.Ordinary) {
                if (horizontals == turn.playerColor.getDirection() && abs(verticals) == 1 && !canEat()) {
                    //Move without eating
                    return squareFrom
                }
                if (abs(horizontals) == 2 && numBetween == 1) {
                    //Move with eating
                    return squareBetween
                }
                //Incorrect move
                return null
            } else {
                if (numBetween == 0 && !canEat()) {
                    //Move without eating
                    return squareFrom
                }
                if (numBetween == 1) {
                    //Move with eating
                    return squareBetween
                }
                //Incorrect move
                return null
            }
        } else {
            //Square "from" is empty
            return null
        }
    }

    //Method checks whether one of the moving player checker eat
    fun canEat() : Boolean {
        val figuresCoords = board.getCoords(whoMoves)

        for ((i, j) in figuresCoords) {
            if (board[i, j].figure?.type == FigureType.Ordinary) {
                for ((di, dj) in listOf(-1 to -1, 1 to -1, 1 to 1, -1 to 1)) {
                    if (board.isValidCoords(i + 2*di, j + 2*dj) &&
                            board[i+2*di, j+2*dj].figure == null &&
                            board[i+di, j+dj].figure?.color ?: whoMoves == whoMoves.nextColor())
                        return true
                }
            } else {
                for ((di, dj) in listOf(-1 to -1, 1 to -1, 1 to 1, -1 to 1)) {
                    var findOpponent = false
                    var d = 1
                    loop@ while (board.isValidCoords(i + d*di, j + d*dj)) {
                        val fig = board[i + d*di, j + d*dj].figure
                        if (fig == null) {
                            if (findOpponent)
                                return true
                        } else {
                            if (fig.color == whoMoves) {
                                break@loop
                            } else {
                                findOpponent = true
                            }
                        }
                        d++
                    }
                }
            }
        }
        return false
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
        canMoveResult.figure = null
        updateState()
        makeQueen(turn)

        if (canMoveResult === board[turn.from] || !canEat()) {
            whoMoves = whoMoves.nextColor()
        }
    }

    override fun updateState() {
        //TODO("not implemented")
    }

}