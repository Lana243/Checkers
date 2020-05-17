package gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import core.Color


class Checker(val color: Color,
              posX: Float,
              posY: Float,
              index: Int,
              setWidth: Float,
              setHeight: Float,
              private var isQueen: Boolean = false,
              var transform: Boolean = false,
              private var time: Int = GUIConstants.initTime,
              private var stage: Int = GUIConstants.initStage,
              val frames: MutableList<TextureRegion> = mutableListOf()) : Actor() {


    init {
        x = posX
        y = posY
        zIndex = index
        setSize(setWidth, setHeight)
        touchable = Touchable.disabled
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (transform) {
            batch!!.draw(frames[stage], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
            time++
            if (time == GUIConstants.maxTime) {
                time = GUIConstants.initTime
                stage++
            }
            if (stage == GUIConstants.lastStage) {
                transform = false
                isQueen = true
            }
        } else if (!isQueen) {
            batch!!.draw(frames[GUIConstants.simpleChecker],
                    x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        } else {
            batch!!.draw(frames[GUIConstants.queenChecker],
                    x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        }
    }
}