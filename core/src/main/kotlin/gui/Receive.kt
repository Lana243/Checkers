package gui

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import core.Color


fun turn(fromX: Int, fromY: Int, toX: Int, toY: Int, game: Game) {
    game.board[toX][toY].checker = game.board[fromX][fromY].checker
    game.board[fromX][fromY].checker = null
    game.board[toX][toY].checker!!.zIndex = GUIConstants.moveZ
    game.board[toX][toY].checker!!.addAction(Actions.moveTo(game.board[toX][toY].x,
            game.board[toX][toY].y, GUIConstants.smooth))
    game.board[toX][toY].checker!!.zIndex = GUIConstants.checkerZ
}

private fun replaceEat(eatBoard: EatBoard, posX: Int, posY: Int, game: Game) {
    game.board[posX][posY].checker!!.addAction(Actions.moveTo(eatBoard.lastX, eatBoard.lastY, GUIConstants.smooth))
    game.board[posX][posY].checker!!.zIndex = GUIConstants.moveZ
    eatBoard.inRow++
    eatBoard.inCol++
    eatBoard.lastX += eatBoard.plusX
    if (eatBoard.inRow == GUIConstants.eatMaxInRow) {
        eatBoard.inRow = 0
        eatBoard.lastY += eatBoard.plusY
        eatBoard.lastX = eatBoard.firstX
    }
    game.board[posX][posY].checker!!.zIndex = GUIConstants.checkerZ
    game.board[posX][posY].checker = null
}

fun eat(posX: Int, posY: Int, game: Game) {
    if (game.board[posX][posY].checker!!.color == Color.WHITE)
        replaceEat(game.blackEatBoard, posX, posY, game)
    else
        replaceEat(game.whiteEatBoard, posX, posY, game)
}

fun becomeQueen(posX: Int, posY: Int, game: Game) {
    game.board[posX][posY].checker!!.zIndex = GUIConstants.moveZ
    val toCenter = ParallelAction(
            Actions.moveTo(GUIConstants.toCenterX, GUIConstants.toCenterY, GUIConstants.smooth),
            Actions.scaleTo(GUIConstants.scaleToCenterWidth, GUIConstants.scaleToCenterHeight, GUIConstants.smooth)
    )
    val back = ParallelAction(
            Actions.moveTo(game.board[posX][posY].x, game.board[posX][posY].y, GUIConstants.smooth),
            Actions.scaleTo(GUIConstants.scaleBackWidth, GUIConstants.scaleBackHeight, GUIConstants.smooth)
    )
    val sequence = SequenceAction(
            toCenter,
            back
    )
    game.board[posX][posY].checker!!.addAction(sequence)
    game.board[posX][posY].checker!!.transform = true
    game.board[posX][posY].checker!!.zIndex = GUIConstants.checkerZ
}
