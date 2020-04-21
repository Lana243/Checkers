class Square(val color: Int) {
    var figure : Figure? = null
    var eaten = false;

    constructor(square: Square) : this(square.color) {
        figure = if (square.figure == null) null else Figure(square.figure!!)
        eaten = square.eaten
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Square) {
            if (this.eaten != other.eaten)
                false
            else {
                val oFigure = other.figure

                val tFigure = this.figure
                if (oFigure != null && tFigure != null) {
                    oFigure.type == tFigure.type && oFigure.color == tFigure.color
                } else {
                    oFigure == null && tFigure == null
                }
            }
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = color
        result = 31 * result + (figure?.hashCode() ?: 0)
        return result
    }
}
