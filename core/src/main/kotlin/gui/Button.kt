package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class Button(
        up: Texture,
        down: Texture,
        posX: Float,
        posY: Float,
        setWidth: Float,
        setHeight: Float,
        private val frames: MutableList<TextureRegion> = mutableListOf(),
        private var now: Int = 0) : Actor() {

    init {
        frames.add(TextureRegion(up))
        frames.add(TextureRegion(down))
        x = posX
        y = posY
        setSize(setWidth, setHeight)
        touchable = Touchable.enabled
        enableTouch()
    }

    private fun enableTouch() {
        addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                now = 0
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                now = 1
                return true
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.draw(frames[now], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}
