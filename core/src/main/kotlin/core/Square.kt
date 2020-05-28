package core

data class Square(val color: Color, val x: Int, val y: Int) {
    var figure: Figure? = null

    /**
     * during one turn we can eat more than one checker
     * but we can't eat one checker twice during the turn
     * @param eaten means that the square contains the checker that was eaten during the current turn
     */
    var eaten = false

    constructor(square: Square) : this(color = square.color, x = square.x, y = square.y) {
        figure = square.figure?.let { Figure(it) }
        eaten = square.eaten
    }
}
