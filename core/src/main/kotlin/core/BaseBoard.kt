package core

interface BaseBoard {

    fun setStartPosition()
    operator fun get(i: Int, j: Int) : Square
    operator fun set(i: Int, j: Int, newSquare: Square)
    operator fun get(coords: Pair<Int, Int>) : Square
    operator fun set(coords: Pair<Int, Int>, newSquare: Square)

    fun print()
}
