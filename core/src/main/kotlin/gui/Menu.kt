package gui

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport


class Menu(val game: Game) : Screen {

    lateinit var stage: Stage
    lateinit var batch: SpriteBatch
    lateinit var checker: Texture
    lateinit var title: Texture
    lateinit var checkerSprite: Sprite
    lateinit var titleSprite: Sprite

    override fun show() {
        batch = SpriteBatch()
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        setBackGround()
        setNewGame()
        setExit()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(Constants.red, Constants.green, Constants.blue, Constants.alpha)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        checkerSprite.draw(batch)
        titleSprite.draw(batch)
        batch.end()
        stage.act(delta)
        stage.draw()
    }

    override fun dispose() {
        batch.dispose()
        checker.dispose()
        title.dispose()
    }

    override fun pause() {}

    override fun hide() {}

    override fun resume() {}

    override fun resize(width: Int, height: Int) {}

}