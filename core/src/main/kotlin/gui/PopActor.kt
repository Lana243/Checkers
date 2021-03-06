package gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import core.Color

open class PopActor(posX: Float,
                    posY: Float,
                    setWidth: Float,
                    setHeight: Float,
                    private val texture: TextureRegion,
                    private val smooth: Float,
                    protected val game: Game,
                    protected val color: Color = Color.WHITE,
                    protected val sequence: SequenceAction = SequenceAction(
                            Actions.scaleTo(GUIConstants.scaleToStarW, GUIConstants.scaleToStarH, smooth),
                            Actions.scaleTo(GUIConstants.scaleFromStarW, GUIConstants.scaleFromStarH, smooth)
                    ),
                    protected val infiniteLoop: RepeatAction = RepeatAction()) : Actor() {
    init {
        x = posX
        y = posY
        setSize(setWidth, setHeight)
        touchable = Touchable.disabled
        scale()
    }

    open fun scale() {
        infiniteLoop.count = RepeatAction.FOREVER
        infiniteLoop.action = sequence
        addAction(infiniteLoop)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}