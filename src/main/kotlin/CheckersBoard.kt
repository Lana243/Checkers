class CheckersBoard(val boardSize: Int) : BaseBoard {
    private val board = Array(boardSize * boardSize) {i -> Square(if ((i / boardSize + i % boardSize) % 2 == 0) -1 else 1)}
    init {
        setStartPosition()
    }

    private fun getIndex(i : Int, j : Int): Int {
        return i * boardSize + j
    }

    fun setEmptyPosition() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                this[i, j].figure = null
            }
        }
    }

    override fun setStartPosition() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                this[i, j].apply {
                    figure = if (color == 1 || i == 3 || i == 4) {
                        null
                    } else {
                        Figure(if (i < 3) Color.WHITE else Color.BLACK)
                    }
                }
            }
        }
    }

    //fun returns coords of squares that contain figures with playerColor
    fun getCoords(playerColor: Color) : List<Pair<Int, Int>> {
        val list = emptyList<Pair<Int, Int>>().toMutableList()
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                this[i, j].figure?.let {
                    if (it.color == playerColor)
                        list.add(i to j)
                }
            }
        }
        return list
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
