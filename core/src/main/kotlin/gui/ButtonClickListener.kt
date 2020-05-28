package gui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class ButtonClickListener(val onTouchUp: ()->Unit): ClickListener() {
    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
        onTouchUp()
    }

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return true
    }
}