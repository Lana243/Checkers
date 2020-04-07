class Figure(col: String) {

    val color : Color = if (col[0] == 'w')
        Color.WHITE
    else
        Color.BLACK
    var type : FigureType = FigureType.Ordinary

}
