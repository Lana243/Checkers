package gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import kotlin.system.exitProcess

fun setNewGame() {
    val skinNew = Skin(Gdx.files.internal(Constants.textSkinPath))
    val newGame = ImageTextButton(Constants.newGameText, skinNew, Constants.styleName)
    newGame.isTransform = true;
    newGame.setScale(Constants.buttonsScale)
    newGame.style.imageUp =
            TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(Constants.buttonPath))))
    newGame.style.imageDown =
            TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(Constants.buttonDownPath))))
    newGame.setPosition(Constants.newGameButtonX, Constants.newGameButtonY)
    newGame.addListener(object : InputListener() {
        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
            Keeper.game = gui.Game(Keeper.menu.game)
            Keeper.menu.game.screen = Keeper.game
        }

        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            return true
        }
    })
    Keeper.menu.stage.addActor(newGame)
}

fun setExit() {
    val skinExit = Skin(Gdx.files.internal(Constants.textSkinPath))
    val exit = ImageTextButton(Constants.exitText, skinExit, Constants.styleName)
    exit.isTransform = true;
    exit.setScale(Constants.buttonsScale)
    exit.style.imageUp =
            TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(Constants.buttonPath))))
    exit.style.imageDown =
            TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(Constants.buttonDownPath))))
    exit.setPosition(Constants.exitButtonX, Constants.exitButtonY)
    exit.addListener(object : InputListener() {
        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
            exitProcess(0);
        }

        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            return true
        }
    })
    Keeper.menu.stage.addActor(exit)
}

fun setBackGround() {
    Keeper.menu.checker = Texture(Constants.checkerImagePath)
    Keeper.menu.checker.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear) //draw background
    Keeper.menu.checkerSprite = Sprite(Keeper.menu.checker)
    Keeper.menu.checkerSprite.setBounds(Constants.checkerImageX, Constants.checkerImageY,
            Keeper.menu.checkerSprite.width * Constants.checkerImageSize,
            Keeper.menu.checkerSprite.height * Constants.checkerImageSize)
    Keeper.menu.checkerSprite.rotation = Constants.checkerImageRotation


    Keeper.menu.title = Texture(Constants.checkerTextPath)
    Keeper.menu.title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    Keeper.menu.titleSprite = Sprite(Keeper.menu.title)
    Keeper.menu.titleSprite.setBounds(Constants.checkerTextX, Constants.checkerTextY,
            Keeper.menu.titleSprite.width * Constants.checkerTextSize,
            Keeper.menu.titleSprite.height * Constants.checkerTextSize)

}