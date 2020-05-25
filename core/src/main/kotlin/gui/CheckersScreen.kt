package gui

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
import core.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess


class CheckersScreen(val game: Game,
                     var colorPlayer: Color) : Screen {

    lateinit var board: Board
    lateinit var stage: Stage
    lateinit var group: Group

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    val guiPlayer = GUIPlayer(colorPlayer)
    private lateinit var gameModel: CheckersGUIGame

    private val newGameButton = Button(Texture(GUIConstants.newGameButtonUpPath),
            Texture(GUIConstants.newGameButtonDownPath),
            GUIConstants.newGameButtonXS, GUIConstants.newGameButtonYS,
            GUIConstants.newGameButtonWidthS, GUIConstants.newGameButtonHeightS)

    private val exitButton = Button(Texture(GUIConstants.exitButtonUpPath),
            Texture(GUIConstants.exitButtonDownPath),
            GUIConstants.exitButtonXS, GUIConstants.exitButtonYS,
            GUIConstants.exitButtonWidthS, GUIConstants.exitButtonHeightS)
    private val menuButton = Button(Texture(GUIConstants.menuButtonUpPath),
            Texture(GUIConstants.menuButtonDownPath),
            GUIConstants.menuButtonXS, GUIConstants.menuButtonYS,
            GUIConstants.menuButtonWidthS, GUIConstants.menuButtonHeightS)


    private fun setBoardEat(color: Color, x: Float, y: Float, texture: Texture): EatenCheckersBoard {
        val eatBoard = EatenCheckersBoard(texture, color, x + GUIConstants.eatBoardDeltaX,
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

    fun gameEnd(state: GameState) {
        logger.log(Level.INFO, "The game is ended!")
        game.setWinner(state)
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
        square.touchable = Touchable.enabled
        square.enableTouch(board)
    }

    private fun setButtons() {

        newGameButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.setNewGame()
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

        menuButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.setMenu()
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
        stage.addActor(newGameButton)
        stage.addActor(exitButton)
        stage.addActor(menuButton)
    }

    private fun initStage() {

        val blackEatBoard = setBoardEat(Color.BLACK, GUIConstants.blackEatX, GUIConstants.blackEatY,
                Texture(GUIConstants.blackEatPath))
        val whiteEatBoard = setBoardEat(Color.WHITE, GUIConstants.whiteEatX, GUIConstants.whiteEatY,
                Texture(GUIConstants.whiteEatPath))

        board = Board(blackEatBoard, whiteEatBoard)
        for (i in 0 until GUIConstants.boardHeight) {
            for (j in 0 until GUIConstants.boardWidth) {
                val texture: Texture = if ((i + j) % GUIConstants.evenSquare == 0) {
                    Texture(GUIConstants.blackSquarePath)
                } else {
                    Texture(GUIConstants.whiteSquarePath)
                }
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
                val square = GUISquare(texture, i, j, GUIConstants.squareZ,
                        j * GUIConstants.squareSpaceX + GUIConstants.squareLeftCornerX,
                        i * GUIConstants.squareSpaceY + GUIConstants.squareLeftCornerY,
                        GUIConstants.squareWidth, GUIConstants.squareHeight, game)
                addSquareListener(square)
                group.addActor(square)
                if (((i + j) % GUIConstants.evenSquare == 0) &&
                        ((i <= GUIConstants.myLastRow) || (i >= GUIConstants.oppositeFirstRow))) {
                    if (i <= GUIConstants.myLastRow) {
                        //if (colorPlayer == Color.WHITE) {
                        placeChecker(Texture(GUIConstants.whiteCheckerPath), square, Color.WHITE,
                                GUIConstants.whiteCheckerSizeX, GUIConstants.whiteCheckerSizeY)
                        /*} else {
                            placeChecker(Texture(GUIConstants.blackCheckerPath), square, Color.BLACK,
                                    GUIConstants.blackCheckerSizeX, GUIConstants.blackCheckerSizeY)
                        }*/
                    } // place white
                    if (i >= GUIConstants.oppositeFirstRow) {
                        //if (colorPlayer == Color.WHITE) {
                        placeChecker(Texture(GUIConstants.blackCheckerPath), square, Color.BLACK,
                                GUIConstants.blackCheckerSizeX, GUIConstants.blackCheckerSizeY)
                        /*} else {
                            placeChecker(Texture(GUIConstants.whiteCheckerPath), square, Color.WHITE,
                                    GUIConstants.whiteCheckerSizeX, GUIConstants.whiteCheckerSizeY)

                        }*/

                    } //place black
                }
                board.squares[i].add(square)
            }
        }
        setButtons()
        stage.addActor(group)

        gameModel = if (colorPlayer == Color.WHITE)
            CheckersGUIGame(CheckersModel(),
                    board,
                    guiPlayer,
                    MinimaxAlphaBetaPlayer("AlphaBeta", Color.BLACK, 10))
        else
            CheckersGUIGame(CheckersModel(),
                    board,
                    MinimaxAlphaBetaPlayer("AlphaBeta", Color.WHITE, 10),
                    guiPlayer)
    }

    inner class Board(private val blackEatenCheckersBoard: EatenCheckersBoard,
                      private val whiteEatenCheckersBoard: EatenCheckersBoard,
                      val squares: MutableList<MutableList<GUISquare>> =
                              MutableList(GUIConstants.boardHeight) { mutableListOf<GUISquare>() }) {


        fun turn(turn: BaseTurn) {
            turn(turn.from.first, turn.from.second, turn.to.first, turn.to.second)
        }

        private fun turn(fromX: Int, fromY: Int, toX: Int, toY: Int) {
            logger.log(Level.INFO, "Try to show turn: $fromX, $fromY to $toX $toY")
            squares[toX][toY].checker = squares[fromX][fromY].checker
            squares[fromX][fromY].checker = null
            squares[toX][toY].checker!!.move(squares[toX][toY].x, squares[toX][toY].y)
        }

        private fun replaceEat(eatenCheckersBoard: EatenCheckersBoard, posX: Int, posY: Int) {
            squares[posX][posY].checker!!.move(eatenCheckersBoard.lastX, eatenCheckersBoard.lastY)
            eatenCheckersBoard.place()
            squares[posX][posY].checker = null
        }

        fun eat(posX: Int, posY: Int) {
            replaceEat(if (squares[posX][posY].checker!!.color == Color.WHITE) blackEatenCheckersBoard
            else whiteEatenCheckersBoard, posX, posY)
        }

        fun becomeQueen(posX: Int, posY: Int) {
            squares[posX][posY].checker!!.turnIntoQueen(squares[posX][posY].x, squares[posX][posY].y)
        }

        fun changeState(state: GameState) {
            if (state != GameState.PLAYING)
                gameEnd(state)
        }

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