class Figure(col: String) {

    val color : Int = if (col[0] == 'w') 1 else -1
    var type : FigureType = FigureType.Ordinary

}
