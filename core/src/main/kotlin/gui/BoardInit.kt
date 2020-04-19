package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


fun setBoardEat(eatBoard: EatBoard, color: Color, x: Float, y: Float) {
    eatBoard.x = x
    eatBoard.y = y
    eatBoard.zIndex = Constants.eatBoardZ
    eatBoard.color = color
    eatBoard.setSize(Constants.eatBoardWidth, Constants.eatBoardHeight)
    eatBoard.lastX = x + Constants.eatBoardDeltaX
    eatBoard.firstX = eatBoard.lastX
    eatBoard.lastY = y + Constants.eatBoardDeltaY
    Keeper.game.group.addActor(eatBoard)
}

fun setCheckerSpriteList(checker: Checker, checkerTexture: Texture, sizeX: Int, sizeY: Int) {
    for (y in 0 until Constants.spriteCheckersInCol) {
        for (x in 0 until Constants.spriteCheckersInRow) {
            checker.frames.add(TextureRegion(checkerTexture, x * sizeX, y * sizeY,
                    sizeX, sizeY))
        }
    }
}

fun placeChecker(checkerTexture: Texture, square: Square, color: Color, sizeX: Int, sizeY: Int) {
    val checker = Checker()
    checkerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    setCheckerSpriteList(checker, checkerTexture, sizeX, sizeY)
    checker.touchable = Touchable.disabled
    checker.setSize(Constants.checkerWidth, Constants.checkerHeight)
    checker.x = square.x
    checker.y = square.y
    checker.zIndex = Constants.checkerZ
    checker.color = color
    square.checker = checker
    Keeper.game.group.addActor(checker)
}

fun addSquareListener(square: Square) {
    square.touchable = Touchable.enabled;
    square.addListener(object : ClickListener() {
        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
            /*
            Send(square.x)
            Send(square.y)
             *///wait for 'send to core data' method
        becomeQueen(square.posX, square.posY)
        }


        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            return true
        }
    })
}

fun initStage() {

    for (i in 0 until Constants.boardHeight) {
        for (j in 0 until Constants.boardWeight) {
            val texture: Texture = if ((i + j) % Constants.evenSquare == 0) {
                Texture(Constants.blackSquarePath)
            } else {
                Texture(Constants.whiteSquarePath)
            }
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            val square = Square(texture)
            square.setPosition(i * Constants.squareSpaceX + Constants.squareLeftCornerX,
                    j * Constants.squareSpaceY + Constants.squareLeftCornerY)
            square.setSize(Constants.squareWidth, Constants.squareHeight)
            square.posX = i
            square.posY = j
            square.zIndex = Constants.squareZ
            addSquareListener(square)
            Keeper.game.group.addActor(square)
            if (((i + j) % Constants.evenSquare == 0) &&
                    ((j <= Constants.myLastRow) || (j >= Constants.oppositeFirstRow))) {
                if (j <= Constants.myLastRow) {
                    placeChecker(Texture(Constants.whiteCheckerPath), square, Color.WHITE,
                            Constants.whiteCheckerSizeX, Constants.whiteCheckerSizeY)
                } // place white
                if (j >= Constants.oppositeFirstRow) {
                    placeChecker(Texture(Constants.blackCheckerPath), square, Color.BLACK,
                            Constants.blackCheckerSizeX, Constants.blackCheckerSizeY)
                } //place black
            }
            Keeper.game.board[i].add(square)
        }
    }

    Keeper.game.blackEatBoard = EatBoard(Texture(Constants.blackEatPath))
    Keeper.game.whiteEatBoard = EatBoard(Texture(Constants.whiteEatPath))
    setBoardEat(Keeper.game.blackEatBoard, Color.BLACK, Constants.blackEatX, Constants.blackEatY)
    setBoardEat(Keeper.game.whiteEatBoard, Color.WHITE, Constants.whiteEatX, Constants.whiteEatY)
    Keeper.game.stage.addActor(Keeper.game.group)
}
