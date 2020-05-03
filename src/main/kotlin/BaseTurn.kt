data class BaseTurn(val playerColor: Color, val from: Pair<Int, Int>, val to: Pair<Int, Int>)

/**
 * Fun checking that all coordinates in the turn are inside the board.
 * @receiver BaseTurn
 * @param boardSize Int
 * @return Boolean
 */
fun BaseTurn.isValid(boardSize : Int) : Boolean {
    return !(this.from.first < 0 || this.from.first >= boardSize ||
            this.from.second < 0 || this.from.second >= boardSize ||
            this.to.first < 0 || this.to.first >= boardSize ||
            this.to.second < 0 || this.to.second >= boardSize)
}
