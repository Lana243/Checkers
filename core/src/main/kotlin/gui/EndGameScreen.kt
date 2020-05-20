package gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import core.GameState
import kotlin.system.exitProcess

class EndGameScreen(val game: Game, private val state: GameState) : Screen {

    lateinit var stage: Stage
    lateinit var back: Texture
    lateinit var batch: SpriteBatch
    lateinit var backSprite: Sprite

    private val menuButton = Button(Texture(GUIConstants.menuButtonUpPath),
            Texture(GUIConstants.menuButtonDownPath),
            GUIConstants.menuButtonX, GUIConstants.menuButtonY,
            GUIConstants.menuButtonWidth, GUIConstants.menuButtonHeight)

    private val exitButton = Button(Texture(GUIConstants.exitButtonUpPath),
            Texture(GUIConstants.exitButtonDownPath),
            GUIConstants.exitButtonXE, GUIConstants.exitButtonYE,
            GUIConstants.exitButtonWidthSE, GUIConstants.exitButtonHeightSE)


    private fun setBackGround() {
        back = Texture(GUIConstants.backImagePath)
        back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        backSprite = Sprite(back)
        backSprite.setBounds(GUIConstants.backPosX, GUIConstants.backPosY,
                GUIConstants.backWidth, GUIConstants.backHeight)

    }

    private fun setButtons() {
        menuButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.setMenu()
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

        stage.addActor(menuButton)
        stage.addActor(exitButton)
    }

    private fun textAction() {
        val texture: Texture = if (state == GameState.BLACK_WINS) {
            Texture(GUIConstants.blackWins)
        } else {
            Texture(GUIConstants.whiteWins)
        }
        val stateAction = PopActor(GUIConstants.stateX, GUIConstants.stateY,
                GUIConstants.stateWidth, GUIConstants.stateHeight,
                TextureRegion(texture), GUIConstants.stateSmooth, game)
        stage.addActor(stateAction)
    }

    override fun show() {
        batch = SpriteBatch()
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        setBackGround()
        setButtons()
        textAction()
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