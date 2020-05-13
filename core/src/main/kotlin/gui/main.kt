package gui

class MyGdxGame : com.badlogic.gdx.Game() {
    override fun create() {
        setScreen(Menu(this));
    }
}
