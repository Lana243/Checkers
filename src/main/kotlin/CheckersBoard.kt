class CheckersBoard(private val boardSize: Int) : BaseBoard {
    init {
        setStartPosition()
    }

    private val board = Array(boardSize * boardSize) {i -> Square(if ((i / boardSize + i % boardSize) % 2 == 0) -1 else 1)}

    override fun setStartPosition() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (i < 3 && board[i * boardSize + j].color == -1) {
                    board[i * boardSize + j].figure = Figure(1)
                }
                if (i == 3 || i == 4 || board[i * boardSize + j].color == 1) {
                    board[i * boardSize + j].figure = null
                }
                if (i >= 5 && board[i * boardSize + j].color == -1) {
                    board[i * boardSize + j].figure = Figure(-1)
                }
            }
        }
    }

    override operator fun get(i: Int, j: Int) : Square {
        return board[i * boardSize + j]
    }

    override operator fun get(coords: Pair<Int, Int>): Square {
        return board[coords.first * boardSize + coords.second]
    }

    override operator fun set(i: Int, j: Int, newSquare: Square) {
        set(Pair(i, j), newSquare)
    }

    override operator fun set(coords: Pair<Int, Int>, newSquare: Square) {
        board[coords.first * boardSize + coords.second] = newSquare
    }

    override fun print() {
        for (i in boardSize - 1 downTo 0) {
            for (j in 0 until boardSize) {
                var t : String
                if (board[i * boardSize + j].figure == null) {
                    t = "."
                } else {
                    t = if (board[i * boardSize + j].figure!!.color == 1) {
                        "w"
                    } else {
                        "b"
                    }
                    if (board[i * boardSize + j].figure!!.type == FigureType.Queen) {
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
