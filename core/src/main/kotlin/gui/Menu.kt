package gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import kotlin.system.exitProcess


class Menu(val game: com.badlogic.gdx.Game) : Screen {

    lateinit var stage: Stage
    lateinit var batch: SpriteBatch
    lateinit var checker: Texture
    lateinit var title: Texture
    lateinit var checkerSprite: Sprite
    lateinit var titleSprite: Sprite

    private fun setNewGame() {
        val skinNew = Skin(Gdx.files.internal(GUIConstants.textSkinPath))
        val newGame = ImageTextButton(GUIConstants.newGameText, skinNew, GUIConstants.styleName)
        newGame.isTransform = true;
        newGame.setScale(GUIConstants.buttonsScale)
        newGame.style.imageUp =
                TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(GUIConstants.buttonPath))))
        newGame.style.imageDown =
                TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(GUIConstants.buttonDownPath))))
        newGame.setPosition(GUIConstants.newGameButtonX, GUIConstants.newGameButtonY)
        newGame.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = Game(game)
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
        stage.addActor(newGame)
    }

    private fun setExit() {
        val skinExit = Skin(Gdx.files.internal(GUIConstants.textSkinPath))
        val exit = ImageTextButton(GUIConstants.exitText, skinExit, GUIConstants.styleName)
        exit.isTransform = true;
        exit.setScale(GUIConstants.buttonsScale)
        exit.style.imageUp =
                TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(GUIConstants.buttonPath))))
        exit.style.imageDown =
                TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(GUIConstants.buttonDownPath))))
        exit.setPosition(GUIConstants.exitButtonX, GUIConstants.exitButtonY)
        exit.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                exitProcess(0);
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
        stage.addActor(exit)
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

    }


    override fun show() {
        batch = SpriteBatch()
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        setBackGround()
        setNewGame()
        setExit()
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