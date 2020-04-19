package core

enum class Color() {
    WHITE,
    BLACK;

    //getDirection() fun return direction of moving for player with current color.
    fun getDirection() = if (this == WHITE) 1 else -1

    //queenLine() fun return index of line
    //  where checker becomes queen for player with current color.
    fun queenLine(boardSize : Int) = if (this == WHITE) boardSize - 1 else 0

    //nextColor() fun returns opposite color.
    fun nextColor() = if (this == WHITE) BLACK else WHITE
}
