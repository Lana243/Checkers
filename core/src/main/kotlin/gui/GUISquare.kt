package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor


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

    override fun draw(batch: Batch?, parentAlpha: Float) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        batch!!.draw(texture, x, y, width, height)
    }

}