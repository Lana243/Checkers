package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InvalidText(posX: Float,
                  posY: Float,
                  setWidth: Float,
                  setHeight: Float,
                  val texture: Texture,
                  private var a: Int = 0)
    : Actor() {

    init {
        x = posX
        y = posY
        setSize(setWidth, setHeight)
        touchable = Touchable.disabled
        isVisible = false
    }

    fun setVisible() {
        isVisible = true
        zIndex = GUIConstants.invalidZIndex.toInt()
        GlobalScope.launch {
            delay(1500)
            isVisible = false
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        setColor(GUIConstants.red, GUIConstants.green, GUIConstants.blue, GUIConstants.alpha)
        batch!!.draw(texture, x, y, width, height)
    }
}