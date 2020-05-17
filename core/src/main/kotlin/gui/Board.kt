package gui

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import core.Color

class Board(val blackEatBoard: EatBoard,
            val whiteEatBoard: EatBoard,
            val squares: MutableList<MutableList<GUISquare>> =
                    MutableList(GUIConstants.boardHeight) { mutableListOf<GUISquare>() }) {

    fun turn(fromX: Int, fromY: Int, toX: Int, toY: Int) {
        squares[toX][toY].checker = squares[fromX][fromY].checker
        squares[fromX][fromY].checker = null
        squares[toX][toY].checker!!.zIndex = GUIConstants.moveZ
        squares[toX][toY].checker!!.addAction(Actions.moveTo(squares[toX][toY].x,
                squares[toX][toY].y, GUIConstants.smooth))
        squares[toX][toY].checker!!.zIndex = GUIConstants.checkerZ
    }

    private fun replaceEat(eatBoard: EatBoard, posX: Int, posY: Int) {
        squares[posX][posY].checker!!.addAction(Actions.moveTo(eatBoard.lastX, eatBoard.lastY, GUIConstants.smooth))
        squares[posX][posY].checker!!.zIndex = GUIConstants.moveZ
        eatBoard.inRow++
        eatBoard.inCol++
        eatBoard.lastX += eatBoard.plusX
        if (eatBoard.inRow == GUIConstants.eatMaxInRow) {
            eatBoard.inRow = 0
            eatBoard.lastY += eatBoard.plusY
            eatBoard.lastX = eatBoard.firstX
        }
        squares[posX][posY].checker!!.zIndex = GUIConstants.checkerZ
        squares[posX][posY].checker = null
    }

    fun eat(posX: Int, posY: Int) {
        if (squares[posX][posY].checker!!.color == Color.WHITE)
            replaceEat(blackEatBoard, posX, posY)
        else
            replaceEat(whiteEatBoard, posX, posY)
    }

    fun becomeQueen(posX: Int, posY: Int) {
        squares[posX][posY].checker!!.zIndex = GUIConstants.moveZ
        val toCenter = ParallelAction(
                Actions.moveTo(GUIConstants.toCenterX, GUIConstants.toCenterY, GUIConstants.smooth),
                Actions.scaleTo(GUIConstants.scaleToCenterWidth, GUIConstants.scaleToCenterHeight, GUIConstants.smooth)
        )
        val back = ParallelAction(
                Actions.moveTo(squares[posX][posY].x, squares[posX][posY].y, GUIConstants.smooth),
                Actions.scaleTo(GUIConstants.scaleBackWidth, GUIConstants.scaleBackHeight, GUIConstants.smooth)
        )
        val sequence = SequenceAction(
                toCenter,
                back
        )
        squares[posX][posY].checker!!.addAction(sequence)
        squares[posX][posY].checker!!.transform = true
        squares[posX][posY].checker!!.zIndex = GUIConstants.checkerZ
    }
}