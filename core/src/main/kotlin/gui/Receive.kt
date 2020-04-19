package gui

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction


fun turn(fromX: Int, fromY: Int, toX: Int, toY: Int) {
    Keeper.game.board[toX][toY].checker = Keeper.game.board[fromX][fromY].checker
    Keeper.game.board[fromX][fromY].checker = null
    Keeper.game.board[toX][toY].checker!!.zIndex = Constants.moveZ
    Keeper.game.board[toX][toY].checker!!.addAction(Actions.moveTo(Keeper.game.board[toX][toY].x,
            Keeper.game.board[toX][toY].y, Constants.smooth))
    Keeper.game.board[toX][toY].checker!!.zIndex = Constants.checkerZ
}

private fun replaceEat(eatBoard: EatBoard, posX: Int, posY: Int) {
    Keeper.game.board[posX][posY].checker!!.addAction(Actions.moveTo(eatBoard.lastX, eatBoard.lastY, Constants.smooth))
    Keeper.game.board[posX][posY].checker!!.zIndex = Constants.moveZ
    eatBoard.inRow++
    eatBoard.inCol++
    eatBoard.lastX += eatBoard.plusX
    if (eatBoard.inRow == Constants.eatMaxInRow) {
        eatBoard.inRow = 0
        eatBoard.lastY += eatBoard.plusY
        eatBoard.lastX = eatBoard.firstX
    }
    Keeper.game.board[posX][posY].checker!!.zIndex = Constants.checkerZ
    Keeper.game.board[posX][posY].checker = null
}

fun eat(posX: Int, posY: Int) {
    if (Keeper.game.board[posX][posY].checker!!.color == Color.WHITE)
        replaceEat(Keeper.game.blackEatBoard, posX, posY)
    else
        replaceEat(Keeper.game.whiteEatBoard, posX, posY)
}

fun becomeQueen(posX: Int, posY: Int) {
    Keeper.game.board[posX][posY].checker!!.zIndex = Constants.moveZ
    val toCenter = ParallelAction(
            Actions.moveTo(Constants.toCenterX, Constants.toCenterY, Constants.smooth),
            Actions.scaleTo(Constants.scaleToCenterWidth, Constants.scaleToCenterHeight, Constants.smooth)
    )
    val back = ParallelAction(
            Actions.moveTo(Keeper.game.board[posX][posY].x, Keeper.game.board[posX][posY].y, Constants.smooth),
            Actions.scaleTo(Constants.scaleBackWidth, Constants.scaleBackHeight, Constants.smooth)
    )
    val sequence = SequenceAction(
            toCenter,
            back
    )
    Keeper.game.board[posX][posY].checker!!.addAction(sequence)
    Keeper.game.board[posX][posY].checker!!.transform = true
    Keeper.game.board[posX][posY].checker!!.zIndex = Constants.checkerZ
}
