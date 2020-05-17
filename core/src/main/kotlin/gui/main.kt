package gui

class Checkers : com.badlogic.gdx.Game() {
    override fun create() {
        setScreen(Menu(this));
    }
}
