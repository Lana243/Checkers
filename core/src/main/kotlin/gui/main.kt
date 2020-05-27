package gui

import core.Color
import core.GameState
import java.util.logging.Level
import java.util.logging.Logger

class Game : com.badlogic.gdx.Game() {

    private lateinit var menuScreen: MenuScreen
    private lateinit var waitScreen: DownloadScreen
    lateinit var checkersScreen: CheckersScreen
    lateinit var chooseScreen: PlayerColorChooseScreen
    private lateinit var gameColor: Color

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    override fun create() {
        gameColor = Color.WHITE
        menuScreen = MenuScreen(this)
        waitScreen = DownloadScreen(this)
        chooseScreen = PlayerColorChooseScreen(this)
        setMenu()
    }

    fun getColor(): Color {
        return gameColor
    }

    fun setColor(newColor: Color) {
        gameColor = newColor
    }

    fun chooseColor() {
        setScreen(chooseScreen)
    }

    fun setMenu() {
        setScreen(MenuScreen(this))
    }

    fun setNewGame() {
        checkersScreen = CheckersScreen(this, gameColor)
        setScreen(checkersScreen)
    }

    fun setWinner(gameState: GameState) {
        logger.log(Level.INFO, "setWinner()")
        setScreen(EndGameScreen(this, gameState))
    }
}
