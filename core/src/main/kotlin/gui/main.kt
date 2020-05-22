package gui

import core.Color
import core.GameState
import kotlin.concurrent.thread

class Game : com.badlogic.gdx.Game() {

    private lateinit var menuScreen: MenuScreen
    private lateinit var waitScreen: DownloadScreen
    lateinit var chooseScreen: PlayerColorChooseScreen
    private lateinit var gameColor: Color

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
        setScreen(menuScreen)
    }

    fun setNewGame() {
        setScreen(waitScreen)
        val v = thread {
            //delay(1000)
            println("lalala")
            setScreen(CheckersScreen(this@Game, gameColor))
        }
        v.join()

    }

    fun setWinner(gameState: GameState) {
        setScreen(EndGameScreen(this, gameState))
    }
}
