enum class Color() {
    WHITE,
    BLACK;
    fun getDirection() = if (this == WHITE) 1 else -1
    fun queenLine(boardSize : Int) = if (this == WHITE) boardSize - 1 else 0
    fun nextColor() = if (this == WHITE) BLACK else WHITE
}
