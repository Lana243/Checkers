abstract class BaseBoard(val boardSize: Int) {

    val board = Array(boardSize) {i -> Array(boardSize) {j -> Square(if ((i + j) % 2 == 0) -1 else 1)} }

    abstract fun setStartPosition()
    abstract operator fun get(i: Int, j: Int) : Square
    abstract operator fun set(i: Int, j: Int, newSquare: Square)
    abstract operator fun get(coords: Pair<Int, Int>) : Square
    abstract operator fun set(coords: Pair<Int, Int>, newSquare: Square)

    abstract fun print()
}
