package gui

import core.Color

class Board(private val blackEatBoard: EatBoard,
            private val whiteEatBoard: EatBoard,
            val squares: MutableList<MutableList<GUISquare>> =
                    MutableList(GUIConstants.boardHeight) { mutableListOf<GUISquare>() }) {

    fun turn(fromX: Int, fromY: Int, toX: Int, toY: Int) {
        squares[toX][toY].checker = squares[fromX][fromY].checker
        squares[fromX][fromY].checker = null
        squares[toX][toY].checker!!.move(squares[toX][toY].x, squares[toX][toY].y)
    }

    private fun replaceEat(eatBoard: EatBoard, posX: Int, posY: Int) {
        squares[posX][posY].checker!!.move(eatBoard.lastX, eatBoard.lastY)
        eatBoard.place()
        squares[posX][posY].checker = null
    }

    fun eat(posX: Int, posY: Int) {
        replaceEat(if (squares[posX][posY].checker!!.color == Color.WHITE) blackEatBoard
        else whiteEatBoard, posX, posY)
    }

    fun becomeQueen(posX: Int, posY: Int) {
        squares[posX][posY].checker!!.turnIntoQueen(squares[posX][posY].x, squares[posX][posY].y)
    }
}