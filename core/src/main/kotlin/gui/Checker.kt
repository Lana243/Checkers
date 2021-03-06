package gui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import core.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger


class Checker(val color: Color,
              posX: Float,
              posY: Float,
              index: Int,
              setWidth: Float,
              setHeight: Float,
              val screen: CheckersScreen,
              private var isQueen: Boolean = false,
              var transform: Boolean = false,
              private var time: Int = GUIConstants.initTime,
              private var stage: Int = GUIConstants.initStage,
              val frames: MutableList<TextureRegion> = mutableListOf())
    : Actor() {

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }


    init {
        x = posX
        y = posY
        zIndex = index
        setSize(setWidth, setHeight)
        touchable = Touchable.disabled
    }

    fun move(toX: Float, toY: Float) {
        zIndex = GUIConstants.moveZ
        logger.log(Level.INFO, "start to move")
        addAction(Actions.moveTo(toX, toY, GUIConstants.smooth))
        Gdx.graphics.requestRendering()
        logger.log(Level.INFO, "moved")
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
        val now: Int
        if (transform) {
            now = stage
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
            now = GUIConstants.simpleChecker
        } else {
            now = GUIConstants.queenChecker
        }
        batch.draw(frames[now], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}