package gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Download(posX: Float,
               posY: Float,
               setWidth: Float,
               setHeight: Float,
               private val texture: TextureRegion) : Actor() {
    init {
        x = posX
        y = posY
        zIndex = GUIConstants.moveZ
        setSize(setWidth, setHeight)
        touchable = Touchable.disabled
        addAction(Actions.rotateBy(GUIConstants.downloadRotation, GUIConstants.smooth * GUIConstants.slower))
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

}