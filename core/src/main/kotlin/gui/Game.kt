package gui

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import core.Color


class Game(val game: Game) : Screen {

    var board = MutableList(GUIConstants.boardHeight) { mutableListOf<gui.GUISquare>() }
    lateinit var blackEatBoard: EatBoard
    lateinit var whiteEatBoard: EatBoard
    lateinit var stage: Stage
    lateinit var group: Group

    private fun setBoardEat(eatBoard: EatBoard, color: Color, x: Float, y: Float) {
        eatBoard.x = x
        eatBoard.y = y
        eatBoard.zIndex = GUIConstants.eatBoardZ
        eatBoard.color = color
        eatBoard.setSize(GUIConstants.eatBoardWidth, GUIConstants.eatBoardHeight)
        eatBoard.lastX = x + GUIConstants.eatBoardDeltaX
        eatBoard.firstX = eatBoard.lastX
        eatBoard.lastY = y + GUIConstants.eatBoardDeltaY
        group.addActor(eatBoard)
    }

    private fun setCheckerSpriteList(checker: Checker, checkerTexture: Texture, sizeX: Int, sizeY: Int) {
        for (y in 0 until GUIConstants.spriteCheckersInCol) {
            for (x in 0 until GUIConstants.spriteCheckersInRow) {
                checker.frames.add(TextureRegion(checkerTexture, x * sizeX, y * sizeY,
                        sizeX, sizeY))
            }
        }
    }

    private fun placeChecker(checkerTexture: Texture, GUISquare: GUISquare, color: Color, sizeX: Int, sizeY: Int) {
        val checker = Checker()
        checkerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        setCheckerSpriteList(checker, checkerTexture, sizeX, sizeY)
        checker.touchable = Touchable.disabled
        checker.setSize(GUIConstants.checkerWidth, GUIConstants.checkerHeight)
        checker.x = GUISquare.x
        checker.y = GUISquare.y
        checker.zIndex = GUIConstants.checkerZ
        checker.color = color
        GUISquare.checker = checker
        group.addActor(checker)
    }

    private fun addSquareListener(GUISquare: GUISquare) {
        GUISquare.touchable = Touchable.enabled;
        GUISquare.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                /**
                 * Send(square.x)
                 * Send(square.y)
                 * @wait for 'send to core data' method
                 */
            }


            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
    }

    private fun initStage() {

        for (i in 0 until GUIConstants.boardHeight) {
            for (j in 0 until GUIConstants.boardWeight) {
                val texture: Texture = if ((i + j) % GUIConstants.evenSquare == 0) {
                    Texture(GUIConstants.blackSquarePath)
                } else {
                    Texture(GUIConstants.whiteSquarePath)
                }
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
                val square = GUISquare(texture)
                square.setPosition(i * GUIConstants.squareSpaceX + GUIConstants.squareLeftCornerX,
                        j * GUIConstants.squareSpaceY + GUIConstants.squareLeftCornerY)
                square.setSize(GUIConstants.squareWidth, GUIConstants.squareHeight)
                square.posX = i
                square.posY = j
                square.zIndex = GUIConstants.squareZ
                addSquareListener(square)
                group.addActor(square)
                if (((i + j) % GUIConstants.evenSquare == 0) &&
                        ((j <= GUIConstants.myLastRow) || (j >= GUIConstants.oppositeFirstRow))) {
                    if (j <= GUIConstants.myLastRow) {
                        placeChecker(Texture(GUIConstants.whiteCheckerPath), square, Color.WHITE,
                                GUIConstants.whiteCheckerSizeX, GUIConstants.whiteCheckerSizeY)
                    } // place white
                    if (j >= GUIConstants.oppositeFirstRow) {
                        placeChecker(Texture(GUIConstants.blackCheckerPath), square, Color.BLACK,
                                GUIConstants.blackCheckerSizeX, GUIConstants.blackCheckerSizeY)
                    } //place black
                }
                board[i].add(square)
            }
        }

        blackEatBoard = EatBoard(Texture(GUIConstants.blackEatPath))
        whiteEatBoard = EatBoard(Texture(GUIConstants.whiteEatPath))
        setBoardEat(blackEatBoard, Color.BLACK, GUIConstants.blackEatX, GUIConstants.blackEatY)
        setBoardEat(whiteEatBoard, Color.WHITE, GUIConstants.whiteEatX, GUIConstants.whiteEatY)
        stage.addActor(group)
    }

    override fun show() {
        stage = Stage(ScreenViewport())
        group = Group()
        Gdx.input.inputProcessor = stage
        initStage()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(GUIConstants.red, GUIConstants.green, GUIConstants.blue, GUIConstants.alpha)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.draw()
        stage.act()
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun resize(width: Int, height: Int) {}
}