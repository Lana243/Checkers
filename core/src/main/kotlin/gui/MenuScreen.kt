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
    private lateinit var viewport: Viewport
    private lateinit var camera: Camera


    private val newGameButton = Button(Texture(GUIConstants.newGameButtonUpPath),
            Texture(GUIConstants.newGameButtonDownPath),
            GUIConstants.newGameButtonX, GUIConstants.newGameButtonY,
            GUIConstants.newGameButtonWidth, GUIConstants.newGameButtonHeight)

    private val exitButton = Button(Texture(GUIConstants.exitButtonUpPath),
            Texture(GUIConstants.exitButtonDownPath),
            GUIConstants.exitButtonX, GUIConstants.exitButtonY,
            GUIConstants.exitButtonWidth, GUIConstants.exitButtonHeight)

    private val colorButton = Button(Texture(GUIConstants.colorButtonUpPath),
            Texture(GUIConstants.colorButtonDownPath),
            GUIConstants.colorButtonX, GUIConstants.colorButtonY,
            GUIConstants.colorButtonWidth, GUIConstants.colorButtonHeight)

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

        colorButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.chooseColor()
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

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

        stage.addActor(PopActor(GUIConstants.starX, GUIConstants.starY, GUIConstants.starSize, GUIConstants.starSize,
                TextureRegion(Texture(GUIConstants.starPath)), GUIConstants.starSmooth, game))
        stage.addActor(PopActor(GUIConstants.starX2, GUIConstants.starY2, GUIConstants.starSize2, GUIConstants.starSize2,
                TextureRegion(Texture(GUIConstants.starPath2)), GUIConstants.starSmooth2, game))
        stage.addActor(PopActor(GUIConstants.starX3, GUIConstants.starY3, GUIConstants.starSize3, GUIConstants.starSize3,
                TextureRegion(Texture(GUIConstants.starPath3)), GUIConstants.starSmooth3, game))
        stage.addActor(PopActor(GUIConstants.starX4, GUIConstants.starY4, GUIConstants.starSize4, GUIConstants.starSize4,
                TextureRegion(Texture(GUIConstants.starPath4)), GUIConstants.starSmooth4, game))
        stage.addActor(PopActor(GUIConstants.starX5, GUIConstants.starY5, GUIConstants.starSize5, GUIConstants.starSize5,
                TextureRegion(Texture(GUIConstants.starPath5)), GUIConstants.starSmooth5, game))
    }

    override fun show() {
        camera = PerspectiveCamera()
        viewport = FitViewport(800f, 480f, camera)
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

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height);
        //stage.setViewport(getFitViewport(stage.getCamera()));
        stage.viewport.update(width, height, true)
    }

}