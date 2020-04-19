package com.mygdx.game

import com.badlogic.gdx.Game
import gui.Keeper
import gui.Menu

class MyGdxGame : Game() {
    override fun create() {
        Keeper.menu = Menu(this)
        setScreen(Keeper.menu);
    }
}
