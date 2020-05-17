package gui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
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

    fun move(toX: Float, toY: Float) {
        zIndex = GUIConstants.moveZ
        addAction(Actions.moveTo(toX, toY, GUIConstants.smooth))
        zIndex = GUIConstants.checkerZ
    }

    fun turnIntoQueen(toX: Float, toY: Float) {
        zIndex = GUIConstants.moveZ
        val toCenter = ParallelAction(
                Actions.moveTo(GUIConstants.toCenterX, GUIConstants.toCenterY, GUIConstants.smooth),
                Actions.scaleTo(GUIConstants.scaleToCenterWidth, GUIConstants.scaleToCenterHeight, GUIConstants.smooth)
        )
        val back = ParallelAction(
                Actions.moveTo(toX, toY, GUIConstants.smooth),
                Actions.scaleTo(GUIConstants.scaleBackWidth, GUIConstants.scaleBackHeight, GUIConstants.smooth)
        )
        val sequence = SequenceAction(
                toCenter,
                back
        )
        addAction(sequence)
        transform = true
        zIndex = GUIConstants.checkerZ
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch == null) throw Exception()
        if (transform) {
            batch.draw(frames[stage], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
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
            batch.draw(frames[GUIConstants.simpleChecker],
                    x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        } else {
            batch.draw(frames[GUIConstants.queenChecker],
                    x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        }
    }
}