class CheckersBoard(val boardSize: Int) : BaseBoard {
    private val board = Array(boardSize * boardSize) {i -> Square(if ((i / boardSize + i % boardSize) % 2 == 0) -1 else 1)}
    init {
        setStartPosition()
    }

    private fun getIndex(i : Int, j : Int): Int {
        return i * boardSize + j
    }

    override fun setStartPosition() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (i < 3 && this[i, j].color == -1) {
                    this[i, j].figure = Figure(1)
                }
                if (i == 3 || i == 4 || this[i, j].color == 1) {
                    this[i, j].figure = null
                }
                if (i >= 5 && this[i, j].color == -1) {
                    this[i, j].figure = Figure(-1)
                }
            }
        }
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
                        val letter = if (it.color == 1) "w" else "b"
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
