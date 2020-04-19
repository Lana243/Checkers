package gui

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport


class Game(val game: Game) : Screen {

    var board = MutableList(Constants.boardHeight) { mutableListOf<gui.Square>() }
    lateinit var blackEatBoard: EatBoard
    lateinit var whiteEatBoard: EatBoard
    lateinit var stage: Stage
    lateinit var group: Group

    override fun show() {
        stage = Stage(ScreenViewport())
        group = Group()
        Gdx.input.inputProcessor = stage
        initStage()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(Constants.red, Constants.green, Constants.blue, Constants.alpha)
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