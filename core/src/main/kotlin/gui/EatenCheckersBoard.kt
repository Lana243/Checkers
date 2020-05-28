package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import core.Color

class EatenCheckersBoard(private val texture: Texture,
                         val color: Color,
                         var lastX: Float,
                         var lastY: Float,
                         private val firstX: Float,
                         posX: Float,
                         posY: Float,
                         index: Int,
                         setWidth: Float,
                         setHeight: Float) : Actor() {

    var inRow: Int = GUIConstants.initEatInRow
    var inCol: Int = GUIConstants.initEatInCol
    private val plusX: Int = GUIConstants.eatDeltaX
    private val plusY: Int = GUIConstants.eatDeltaY

    init {
        x = posX
        y = posY
        zIndex = index
        setSize(setWidth, setHeight)
    }

    fun place() {
        inRow++
        inCol++
        lastX += plusX
        if (inRow == GUIConstants.eatMaxInRow) {
            inRow = 0
            lastY += plusY
            lastX = firstX
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        batch!!.draw(texture, x, y, width, height)
    }
}