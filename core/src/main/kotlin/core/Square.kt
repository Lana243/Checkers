package core

class Square(var color: Int) {
    var figure : Figure? = null

    override fun equals(other: Any?): Boolean {
        return if (other is Square) {
            val oFigure = other.figure

            val tFigure = this.figure
            if (oFigure != null && tFigure != null) {
                oFigure.type == tFigure.type && oFigure.color == tFigure.color
            } else {
                oFigure == null && tFigure == null
            }
        } else {
            false
        }
    }
}
