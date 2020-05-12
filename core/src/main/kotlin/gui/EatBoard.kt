package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import core.Color
import kotlin.properties.Delegates

class EatBoard(private var texture: Texture) : Actor() {

    lateinit var color: Color

    var inRow: Int = GUIConstants.initEatInRow
    var inCol: Int = GUIConstants.initEatInCol
    var lastX by Delegates.notNull<Float>()
    var lastY by Delegates.notNull<Float>()
    var firstX by Delegates.notNull<Float>()
    var plusX: Int = GUIConstants.eatDeltaX
    var plusY: Int = GUIConstants.eatDeltaY

    override fun draw(batch: Batch?, parentAlpha: Float) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        batch!!.draw(texture, x, y, width, height)
    }
}