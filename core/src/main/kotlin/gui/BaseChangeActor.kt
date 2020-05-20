package gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable

class BaseChangeActor(
        posX: Float,
        posY: Float,
        setWidth: Float,
        setHeight: Float,
        val frames: MutableList<TextureRegion> = mutableListOf(),
        private var now: Int = GUIConstants.whiteCheckerChooseState) : Actor() {

    init {
        x = posX
        y = posY
        setSize(setWidth, setHeight)
        touchable = Touchable.disabled
    }

    fun changeState(state: Int) {
        now = state
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.draw(frames[now], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}