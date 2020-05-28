package core

data class Figure(val color : Color, var type : FigureType = FigureType.Ordinary) {
    constructor(figure: Figure) : this(figure.color, figure.type)
}
