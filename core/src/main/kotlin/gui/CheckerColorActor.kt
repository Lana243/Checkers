package gui

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import core.Color

class CheckerColorActor(posX: Float,
                        posY: Float,
                        setWidth: Float,
                        setHeight: Float,
                        texture: TextureRegion,
                        smooth: Float,
                        color: Color,
                        game: Game) : PopActor(posX, posY, setWidth, setHeight, texture, smooth, game, color) {
    private var otherChecker: CheckerColorActor? = null

    init {

        val state = if (color == Color.WHITE) {
            GUIConstants.whiteCheckerChooseState
        } else {
            GUIConstants.blackCheckerChooseState
        }

        touchable = Touchable.enabled
        addListener(object : ClickListener() {

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                game.setColor(color)
                scale()
                game.chooseScreen.changeState(state)
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

    }

    fun setOther(checker: CheckerColorActor) {
        otherChecker = checker
    }

    override fun scale() {
        clearActions()
        otherChecker?.clearActions()
        infiniteLoop.count = RepeatAction.FOREVER
        infiniteLoop.action = sequence
        if (game.getColor() == color) {
            addAction(infiniteLoop)
        } else {
            otherChecker?.addAction(infiniteLoop)
        }
    }
}