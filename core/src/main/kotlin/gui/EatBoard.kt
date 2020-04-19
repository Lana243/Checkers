package gui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import kotlin.properties.Delegates

class EatBoard(private var texture: Texture) : Actor() {

    lateinit var color: Color

    var inRow: Int = Constants.initEatInRow
    var inCol: Int = Constants.initEatInCol
    var lastX by Delegates.notNull<Float>()
    var lastY by Delegates.notNull<Float>()
    var firstX by Delegates.notNull<Float>()
    var plusX: Int = Constants.eatDeltaX
    var plusY: Int = Constants.eatDeltaY

    override fun draw(batch: Batch?, parentAlpha: Float) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        batch!!.draw(texture, x, y, width, height)
    }
}