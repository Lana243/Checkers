package gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import kotlin.system.exitProcess


class MenuScreen(private val game: gui.Game) : Screen {

    lateinit var stage: Stage
    private lateinit var batch: SpriteBatch
    lateinit var checker: Texture
    lateinit var title: Texture
    lateinit var checkerSprite: Sprite
    lateinit var titleSprite: Sprite


    private val newGameButton = Button(Texture(GUIConstants.newGameButton.upPath),
            Texture(GUIConstants.newGameButton.downPath),
            GUIConstants.newGameButton.x, GUIConstants.newGameButton.y,
            GUIConstants.newGameButton.width, GUIConstants.newGameButton.height)

    private val exitButton = Button(Texture(GUIConstants.exitButton.upPath),
            Texture(GUIConstants.exitButton.downPath),
            GUIConstants.exitButton.x, GUIConstants.exitButton.y,
            GUIConstants.exitButton.width, GUIConstants.exitButton.height)

    private val colorButton = Button(Texture(GUIConstants.colorButton.upPath),
            Texture(GUIConstants.colorButton.downPath),
            GUIConstants.colorButton.x, GUIConstants.colorButton.y,
            GUIConstants.colorButton.width, GUIConstants.colorButton.height)

    private fun setButtons() {

        newGameButton.addListener(ButtonClickListener { game.setNewGame() })
        exitButton.addListener(ButtonClickListener { exitProcess(0) })
        colorButton.addListener(ButtonClickListener {game.chooseColor() })

        stage.addActor(newGameButton)
        stage.addActor(exitButton)
        stage.addActor(colorButton)
    }

    private fun setBackGround() {
        checker = Texture(GUIConstants.checkerImagePath)
        checker.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear) //draw background
        checkerSprite = Sprite(checker)
        checkerSprite.setBounds(GUIConstants.checkerImageX, GUIConstants.checkerImageY,
                checkerSprite.width * GUIConstants.checkerImageSize,
                checkerSprite.height * GUIConstants.checkerImageSize)
        checkerSprite.rotation = GUIConstants.checkerImageRotation


        title = Texture(GUIConstants.checkerTextPath)
        title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        titleSprite = Sprite(title)
        titleSprite.setBounds(GUIConstants.checkerTextX, GUIConstants.checkerTextY,
                titleSprite.width * GUIConstants.checkerTextSize,
                titleSprite.height * GUIConstants.checkerTextSize)

        for (i in GUIConstants.starPath.indices) {
            stage.addActor(PopActor(GUIConstants.starX[i], GUIConstants.starY[i], GUIConstants.starSize[i], GUIConstants.starSize[i],
                TextureRegion(Texture(GUIConstants.starPath[i])), GUIConstants.starSmooth[i], game))
        }
    }

    override fun show() {
        batch = SpriteBatch()
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        setBackGround()
        setButtons()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(GUIConstants.red, GUIConstants.green, GUIConstants.blue, GUIConstants.alpha)
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