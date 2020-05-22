package core

data class Square(val color: Color, val X: Int, val Y: Int) {
    var figure: Figure? = null

    /**
     * during one turn we can eat more than one checker
     * but we can't eat one checker twice during the turn
     * @param eaten means that the square contains the checker that was eaten during the current turn
     */
    var eaten = false

    constructor(square: Square) : this(color = square.color, X = square.X, Y = square.Y) {
        figure = square.figure?.let { Figure(it) }
        eaten = square.eaten
    }
}
