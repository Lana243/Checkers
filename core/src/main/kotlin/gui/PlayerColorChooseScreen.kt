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
import core.Color

class PlayerColorChooseScreen(val game: Game) : Screen {

    lateinit var stage: Stage
    lateinit var back: Texture
    lateinit var batch: SpriteBatch
    lateinit var backSprite: Sprite
    lateinit var text: BaseChangeActor

    private val menuButton = Button(Texture(GUIConstants.menuButtonUpPath),
            Texture(GUIConstants.menuButtonDownPath),
            GUIConstants.menuButtonX, GUIConstants.menuButtonY,
            GUIConstants.menuButtonWidth, GUIConstants.menuButtonHeight)

    private fun setButtons() {
        menuButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.setMenu()
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
        stage.addActor(menuButton)
    }

    private fun setBackGround() {
        back = Texture(GUIConstants.backImagePath)
        back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        backSprite = Sprite(back)
        backSprite.setBounds(GUIConstants.backPosX, GUIConstants.backPosY,
                GUIConstants.backWidth, GUIConstants.backHeight)

    }

    fun changeState(state: Int) {
        text.changeState(state)
    }

    private fun setText() {
        val texture = Texture(GUIConstants.checkerChoosePath)
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        text = BaseChangeActor(GUIConstants.checkerChooseX, GUIConstants.checkerChooseY,
                GUIConstants.checkerChooseWF, GUIConstants.checkerChooseHF)

        for (y in 0 until GUIConstants.checkersColorsCount) {
            text.frames.add(TextureRegion(texture, 0, y * GUIConstants.checkerChooseH,
                    GUIConstants.checkerChooseW, GUIConstants.checkerChooseH))
        }
        stage.addActor(text)
    }

    private fun setCheckers() {

        val black = CheckerColorActor(GUIConstants.blackCheckerChooseX, GUIConstants.blackCheckerChooseY,
                GUIConstants.blackCheckerChooseSize, GUIConstants.blackCheckerChooseSize,
                TextureRegion(Texture(GUIConstants.blackCheckerChoose)),
                GUIConstants.blackCheckerChooseSmooth,
                Color.BLACK, game)

        val white = CheckerColorActor(GUIConstants.whiteCheckerChooseX, GUIConstants.whiteCheckerChooseY,
                GUIConstants.whiteCheckerChooseSize, GUIConstants.whiteCheckerChooseSize,
                TextureRegion(Texture(GUIConstants.whiteCheckerChoose)),
                GUIConstants.whiteCheckerChooseSmooth,
                Color.WHITE, game)

        white.setOther(black)
        black.setOther(white)
        stage.addActor(black)
        stage.addActor(white)
    }

    override fun show() {
        batch = SpriteBatch()
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        setBackGround()
        setCheckers()
        setText()
        setButtons()
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

    override fun resize(width: Int, height: Int) {}


    override fun hide() {}
}