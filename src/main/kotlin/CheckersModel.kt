import kotlin.math.abs
import kotlin.math.sign

data class CheckersModel(val board : CheckersBoard = CheckersBoard(8)) : BaseModel() {
    
    constructor(model : CheckersModel) : this(CheckersBoard(model.board))  {
        whoMoves = model.whoMoves
        gameState = model.gameState
        eatingChecker = model.eatingChecker
    }

    /**
     * We divided one long turn with many eaten checkers into many turns with one eaten checker
     * So we have to check that during one turn player moves only one checker
     * @param eatingChecker is the checker that player moves during one turn
     */
    var eatingChecker : Square? = null

    private var eatenList = ArrayList<Square>()
    /**
     * This functions checking turn for legacy and rules and also find checker, that will be eaten.
         Its return value has 3 options:
            if turn is illegal,
                it returns null,
            if turn is legal but there aren't any checker will be eaten,
                it returns square, from which the turn started
            if turn is legal and some checker must be eaten,
                it return square, where this checker is located
     * @param turn BaseTurn
     * @return Square?
     */
    override fun canMove(turn: BaseTurn): Square? {

        if (turn.playerColor != whoMoves) {
            /**
             * player's color isn't correct
             */
            return null
        }

        if (!turn.isValid(board.boardSize)) {
            return null
        }

        val (squareFrom, squareTo) = board[turn]
        if (eatingChecker != null && eatingChecker != squareFrom) {
            /**
             * try to move not the checker that now in eating process
             */
            return null
        }
        if (squareFrom.color == 1 || squareTo.color == 1) {
            return null
        }
        val squareFromFigure = squareFrom.figure
        if (squareFromFigure != null) {
            if (squareTo.figure != null) {
                /**
                 * square to is not empty - can't move there
                 */
                return null
            }
            if (squareFromFigure.color != turn.playerColor) {
                /**
                 * figure's color on "from" square isn't equal to player's color - illegal move
                 */
                return null
            }
            val verticals = turn.to.second - turn.from.second
            val horizontals = turn.to.first - turn.from.first
            if (abs(verticals) != abs(horizontals)) {
                /**
                 * Move is not diagonal
                 */
                return null
            }
            var numBetween = 0
            var squareBetween = squareFrom
            for (d in 1 until abs(verticals)) {
                val x = turn.from.first + horizontals.sign * d
                val y = turn.from.second + verticals.sign * d

                if (board[x, y].eaten) {
                    /**
                     * square contained eaten checker
                     */
                    return null
                }
                if (board[x, y].figure != null) {
                    if (board[x, y].figure?.color == whoMoves) {
                        /**
                         * The checker between 'from' and 'to' squares has same color with moving player
                         */
                        return null
                    } else {
                        numBetween++
                        squareBetween = board[x, y]
                    }
                }
            }
            if (numBetween > 1) {
                /**
                 * More then 1 checker between 'from' and 'to' squares
                 */
                return null
            }
            when (squareFromFigure.type) {
                FigureType.Ordinary -> {
                    if (horizontals == turn.playerColor.getDirection() && abs(verticals) == 1 && !canEat()) {
                        /**
                         * Move without eating
                         */
                        return squareFrom
                    }
                    if (abs(horizontals) == 2 && numBetween == 1) {
                        /**
                         * Move with eating
                         */
                        return squareBetween
                    }
                    /**
                     * Incorrect move
                     */
                    return null
                }
                FigureType.Queen -> {
                    if (numBetween == 0 && !canEat()) {
                        /**
                         * Move without eating
                         */
                        return squareFrom
                    }
                    if (numBetween == 1) {
                        /**
                         * Move with eating
                         */
                        return squareBetween
                    }
                    /**
                     * Incorrect move
                     */
                    return null
                }
            }
        } else {
            /**
             * Square "from" is empty
             */
            return null
        }
    }

    /**
     * Method checks whether one of the moving player checker eat
     * @return Boolean
     */
    fun canEat() : Boolean {
        val figuresCoords = board.getCoords(whoMoves)

        for ((i, j) in figuresCoords) {
            if (canEat(i to j))
                return true
        }
        return false
    }

    private fun canEat(squareFromCoords : Pair<Int, Int>): Boolean {
        val (i, j) = squareFromCoords
        when (board[i, j].figure?.type) {
            FigureType.Ordinary -> {
                for ((di, dj) in listOf(-1 to -1, 1 to -1, 1 to 1, -1 to 1)) {
                    if (board.isValidCoords(i + 2 * di, j + 2 * dj) &&
                        board[i + 2 * di, j + 2 * dj].figure == null &&
                        board[i + di, j + dj].figure?.color ?: whoMoves == whoMoves.nextColor() &&
                        !board[i + di, j + dj].eaten
                    )
                        return true
                }
            }
            FigureType.Queen -> {
                for ((di, dj) in listOf(-1 to -1, 1 to -1, 1 to 1, -1 to 1)) {
                    var findOpponent = false
                    var d = 1
                    loop@ while (board.isValidCoords(i + d * di, j + d * dj)) {
                        if (board[i + d * di, j + d * dj].eaten) {
                            break@loop
                        }
                        val fig = board[i + d * di, j + d * dj].figure
                        if (fig == null) {
                            if (findOpponent)
                                return true
                        } else {
                            if (fig.color == whoMoves) {
                                break@loop
                            } else {
                                if (findOpponent)
                                    break@loop
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

    private fun updateEatenList() {
        for (i in 0 until eatenList.size) {
            eatenList[i].eaten = false
        }
        eatenList.clear()
    }

    override fun move(turn: BaseTurn)  {
        val canMoveResult = canMove(turn) ?: throw IllegalStateException(
                "Turn isn't valid now, but model is trying to apply it.")
        if (canMoveResult !== board[turn.from]) {
            canMoveResult.eaten = true
            eatenList.add(canMoveResult)
        }
        board[turn.to].figure = board[turn.from].figure
        board[turn.from].figure = null
        canMoveResult.figure = null
        makeQueen(turn)
        if (canMoveResult === board[turn.from] || !canEat(turn.to)) {
            whoMoves = whoMoves.nextColor()
            eatingChecker = null
            updateEatenList()
        } else {
            eatingChecker = board[turn.to]
        }
        updateState()
    }

    override fun updateState() {
        if (board.countCheckers(Color.WHITE) == 0) {
            gameState = GameState.BLACK_WINS
        }
        if (board.countCheckers(Color.BLACK) == 0) {
            gameState = GameState.WHITE_WINS
        }
        if (possibleTurns().isEmpty()) {
            gameState = if (whoMoves == Color.BLACK)
                GameState.WHITE_WINS
            else
                GameState.BLACK_WINS
        }
    }

    fun possibleTurns() : List<BaseTurn> {
        val list = emptyList<BaseTurn>().toMutableList()
        for ((iFrom, jFrom) in board.getCoords(whoMoves)) {
            for ((iTo, jTo) in List(board.boardSize * board.boardSize)
            { it -> it / board.boardSize to it % board.boardSize}) {
                val turn = BaseTurn(whoMoves, iFrom to jFrom, iTo to jTo)
                if (canMove(turn) != null) {
                    list.add(turn)
                }
            }
        }
        return list
    }
}
