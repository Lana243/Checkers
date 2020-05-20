package gui

import core.Color
import core.GameState

class Game : com.badlogic.gdx.Game() {

    lateinit var menu: Menu
    lateinit var choose: PlayerColorChoose
    private lateinit var gameColor: Color

    override fun create() {
        gameColor = Color.WHITE
        menu = Menu(this)
        choose = PlayerColorChoose(this)
        setMenu()
    }

    fun getColor(): Color {
        return gameColor
    }

    fun setColor(newColor: Color) {
        gameColor = newColor
    }

    fun chooseColor() {
        setScreen(choose)
    }

    fun setMenu() {
        setScreen(menu)
    }

    fun setNewGame() {
        setScreen(Checkers(this, gameColor))
    }

    fun setWinner(gameState: GameState) {
        setScreen(EndGame(this, gameState))
    }
}
