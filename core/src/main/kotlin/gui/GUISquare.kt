package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import core.GameState


class GUISquare(private val texture: Texture,
                val posX: Int,
                val posY: Int,
                index: Int,
                setPosX: Float,
                setPosY: Float,
                width: Float,
                height: Float,
                var checker: Checker? = null) : Actor() {

    init {
        zIndex = index
        setPosition(setPosX, setPosY)
        setSize(width, height)
    }

    fun enableTouch(board: CheckersScreen.Board) {
        addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                board.changeState(GameState.WHITE_WINS)
                /**
                 * Send(square.x)
                 * Send(square.y)
                 * @wait for 'send to core data' method
                 */
            }


            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        batch!!.draw(texture, x, y, width, height)
    }

}