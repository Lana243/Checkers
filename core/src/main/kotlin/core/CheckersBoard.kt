package core

data class CheckersBoard(val boardSize: Int) : BaseBoard {
    private val board = Array(boardSize * boardSize) { i -> Square(if ((i / boardSize + i % boardSize) % 2 == 0) Color.BLACK else Color.WHITE, i / boardSize, i % boardSize) }

    init {
        setStartPosition()
    }

    constructor(other: CheckersBoard) : this(other.boardSize) {
        forEachSquare { i, j, _ -> this[i, j] = Square(other[i, j])}
    }

    private fun forEachSquare(action: (Int, Int, Square) -> Unit) {
        for (i in 0 until boardSize)
            for (j in 0 until boardSize)
                action(i, j, this[i, j])
    }

    override fun equals(other: Any?) = other is CheckersBoard && boardSize == other.boardSize && board.contentEquals(other.board)

    private fun getIndex(i : Int, j : Int): Int {
        return i * boardSize + j
    }

    fun setEmptyPosition() {
        forEachSquare { _, _, s -> s.figure = null}
    }

    override fun setStartPosition() {
        forEachSquare { i, _, s -> s.figure = if (s.color == Color.WHITE || i == 3 || i == 4) null else Figure(if (i < 3) Color.WHITE else Color.BLACK) }
    }

    /**
     * Fun returns coords of squares that contain figures with playerColor
     * @param playerColor Color
     * @return List<Pair<Int, Int>>
     */
    fun getCoords(playerColor: Color) : List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        forEachSquare { i, j, s -> if (s.figure?.color == playerColor) list.add(i to j)}
        return list
    }

    /**
     * This method counts number of checkers with color = color
     * @param color Color
     * @return Int
     */
    fun countCheckers(color: Color) : Int = board.count { it.figure?.color == color }

    /**
     * This method counts number of queen checkers with color = color
     * @param color Color
     * @return Int
     */
    fun countQueenCheckers(color: Color): Int = board.count { it.figure?.color == color && it.figure?.type == FigureType.Queen }

    /**
     * This method checks whether the coords is valid
     * @param i Int
     * @param j Int
     * @return Boolean
     */
    fun isValidCoords(i : Int, j : Int) : Boolean {
        return (i >= 0 && j >= 0 && i < boardSize && j < boardSize)
    }

    override operator fun get(i: Int, j: Int) : Square {
        return board[getIndex(i, j)]
    }

    override operator fun get(coords: Pair<Int, Int>): Square {
        return board[getIndex(coords.first, coords.second)]
    }

    operator fun get(turn : BaseTurn) : Pair<Square, Square> {
        return get(turn.from) to get(turn.to)
    }

    override operator fun set(i: Int, j: Int, newSquare: Square) {
        set(Pair(i, j), newSquare)
    }

    override operator fun set(coords: Pair<Int, Int>, newSquare: Square) {
        board[getIndex(coords.first, coords.second)] = newSquare
    }

    override fun print() {
        for (i in boardSize - 1 downTo 0) {
            for (j in 0 until boardSize) {
                val t = this[i, j].figure.let {
                    if (it == null) {
                        "."
                    } else {
                        val letter = if (it.color == Color.WHITE) "w" else "b"
                        if (it.type == FigureType.Queen) {
                            letter.toUpperCase()
                        } else {
                            letter
                        }
                    }
                }
                print(t)
            }
            println()
        }
        println()
    }
}
