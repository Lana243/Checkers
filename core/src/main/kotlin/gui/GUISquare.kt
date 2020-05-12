package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import kotlin.properties.Delegates.notNull


class GUISquare(private val texture: Texture) : Actor() {

    var checker: Checker? = null
    var posX by notNull<Int>()
    var posY by notNull<Int>()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        batch!!.draw(texture, x, y, width, height)
    }

}