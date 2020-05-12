package gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import core.Color


class Checker() : Actor() {

    var isQueen: Boolean = false
    var transform: Boolean = false
    var time: Int = GUIConstants.initTime
    var stage: Int = GUIConstants.initStage
    lateinit var color: Color
    var frames = mutableListOf<TextureRegion>()

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