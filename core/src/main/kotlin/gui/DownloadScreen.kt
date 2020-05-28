package gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport

class DownloadScreen(val game: Game) : Screen {
    lateinit var stage: Stage
    lateinit var back: Texture
    lateinit var batch: SpriteBatch
    lateinit var backSprite: Sprite

    private fun setBackGround() {
        back = Texture(GUIConstants.backWaitImagePath)
        back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        backSprite = Sprite(back)
        backSprite.setBounds(GUIConstants.backPosX, GUIConstants.backPosY,
                GUIConstants.backWidth, GUIConstants.backHeight)

    }

    override fun show() {
        batch = SpriteBatch()
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        setBackGround()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(GUIConstants.red, GUIConstants.green, GUIConstants.blue, GUIConstants.alpha)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        backSprite.draw(batch)
        batch.end()
        stage.act(delta)
        stage.draw()
    }

    override fun dispose() {
        batch.dispose()
        stage.dispose()
        back.dispose()
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun resize(width: Int, height: Int) {}

}