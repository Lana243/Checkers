class Figure(val color : Int) {
  
    constructor(col : String) : this(if (col[0] == 'w') 1 else -1)
  
    var type : FigureType = FigureType.Ordinary
}
