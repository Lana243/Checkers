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
import kotlin.system.exitProcess


class Game(val game: Game) : Screen {

    lateinit var board: Board
    lateinit var stage: Stage
    lateinit var group: Group
    private val newGameButton = Button(Texture(GUIConstants.newGameButtonUpPath),
            Texture(GUIConstants.newGameButtonDownPath),
            GUIConstants.newGameButtonXS, GUIConstants.newGameButtonYS,
            GUIConstants.newGameButtonWidthS, GUIConstants.newGameButtonHeightS)

    private val exitButton = Button(Texture(GUIConstants.exitButtonUpPath),
            Texture(GUIConstants.exitButtonDownPath),
            GUIConstants.exitButtonXS, GUIConstants.exitButtonYS,
            GUIConstants.exitButtonWidthS, GUIConstants.exitButtonHeightS)


    private fun setBoardEat(color: Color, x: Float, y: Float, texture: Texture): EatBoard {
        val eatBoard = EatBoard(texture, color, x + GUIConstants.eatBoardDeltaX,
                y + GUIConstants.eatBoardDeltaY, x + GUIConstants.eatBoardDeltaX,
                x, y, GUIConstants.eatBoardZ, GUIConstants.eatBoardWidth, GUIConstants.eatBoardHeight)
        group.addActor(eatBoard)
        return eatBoard
    }

    private fun setCheckerSpriteList(checker: Checker, checkerTexture: Texture, sizeX: Int, sizeY: Int) {
        for (y in 0 until GUIConstants.spriteCheckersInCol) {
            for (x in 0 until GUIConstants.spriteCheckersInRow) {
                checker.frames.add(TextureRegion(checkerTexture, x * sizeX, y * sizeY,
                        sizeX, sizeY))
            }
        }
    }

    private fun placeChecker(checkerTexture: Texture, guiSquare: GUISquare, color: Color, sizeX: Int, sizeY: Int) {
        checkerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        val checker = Checker(color, guiSquare.x, guiSquare.y, GUIConstants.checkerZ,
                GUIConstants.checkerWidth, GUIConstants.checkerHeight)
        setCheckerSpriteList(checker, checkerTexture, sizeX, sizeY)
        guiSquare.checker = checker
        group.addActor(checker)
    }

    private fun addSquareListener(square: GUISquare) {
        square.touchable = Touchable.enabled;
        square.enableTouch(board, square)
    }

    private fun setButtons() {

        newGameButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = Game(game)
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

        exitButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                exitProcess(0)
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

        stage.addActor(newGameButton)
        stage.addActor(exitButton)
    }

    private fun initStage() {

        val blackEatBoard = setBoardEat(Color.WHITE, GUIConstants.whiteEatX, GUIConstants.whiteEatY,
                Texture(GUIConstants.whiteEatPath))
        val whiteEatBoard = setBoardEat(Color.BLACK, GUIConstants.blackEatX, GUIConstants.blackEatY,
                Texture(GUIConstants.blackEatPath))

        board = Board(blackEatBoard, whiteEatBoard)
        for (i in 0 until GUIConstants.boardHeight) {
            for (j in 0 until GUIConstants.boardWeight) {
                val texture: Texture = if ((i + j) % GUIConstants.evenSquare == 0) {
                    Texture(GUIConstants.blackSquarePath)
                } else {
                    Texture(GUIConstants.whiteSquarePath)
                }
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
                val square = GUISquare(texture, i, j, GUIConstants.squareZ,
                        i * GUIConstants.squareSpaceX + GUIConstants.squareLeftCornerX,
                        j * GUIConstants.squareSpaceY + GUIConstants.squareLeftCornerY,
                        GUIConstants.squareWidth, GUIConstants.squareHeight)
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
                board.squares[i].add(square)
            }
        }
        setButtons()
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