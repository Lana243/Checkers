package gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor


class Checker() : Actor() {

    var isQueen: Boolean = false
    var transform: Boolean = false
    var time: Int = Constants.initTime
    var stage: Int = Constants.initStage
    lateinit var color: Color
    var frames = mutableListOf<TextureRegion>()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (transform) {
            batch!!.draw(frames[stage], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
            time++
            if (time == Constants.maxTime) {
                time = Constants.initTime
                stage++
            }
            if (stage == Constants.lastStage) {
                transform = false
                isQueen = true
            }
        } else if (!isQueen) {
            batch!!.draw(frames[Constants.simpleChecker],
                    x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        } else {
            batch!!.draw(frames[Constants.queenChecker],
                    x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        }
    }
}