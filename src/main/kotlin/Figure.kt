class Figure(col: String) {

    val color : Color
    var type : FigureType = FigureType.Ordinary

    init {
        color = if (col[0] == 'w')
            Color.WHITE
        else
            Color.BLACK
    }

}
