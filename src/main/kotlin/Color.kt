enum class Color() {
    WHITE,
    BLACK;

    /**
     * getDirection() fun return direction of moving for player with current color.
     * @return Int
     */
    fun getDirection() = if (this == WHITE) 1 else -1

    /**
     * queenLine() fun return index of line
     * where checker becomes queen for player with current color.
     * @param boardSize Int
     * @return Int
     */
    fun queenLine(boardSize : Int) = if (this == WHITE) boardSize - 1 else 0

    /**
     * nextColor() fun returns opposite color.
     * @return Color
     */
    fun nextColor() = if (this == WHITE) BLACK else WHITE
}
