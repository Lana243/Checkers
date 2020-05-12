package com.mygdx.game

import com.badlogic.gdx.Game
import gui.Menu

class MyGdxGame : Game() {
    override fun create() {
        setScreen(Menu(this));
    }
}
