class Figure(val color : Color) {
    var type : FigureType = FigureType.Ordinary

    constructor(col : String) : this(if (col[0] == 'w') Color.WHITE else Color.BLACK)
}
