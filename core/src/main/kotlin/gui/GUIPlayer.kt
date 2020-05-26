package gui

import com.badlogic.gdx.utils.compression.lzma.Base
import core.BaseGame
import core.BaseTurn
import core.CheckersModel
import core.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.lang.Exception
import java.util.logging.Level
import java.util.logging.Logger

class GUIPlayer(color: Color) : core.BasePlayer<CheckersModel>(color) {

    companion object {
        private val logger = Logger.getLogger(this::class.simpleName)
    }

    override fun makeTurn() {
        baseGame.makeTurn(BaseTurn(color, cache[0], cache[1]))
        cache.clear()
    }

    override fun update() {
        cache.clear()
    }

    override fun illegalTurn() {
        (baseGame as CheckersGUIGame).board.invalidTurn()
        update()
    }

    private val cache = mutableListOf<Pair<Int, Int>>()

    fun addXY(horizontal: Int, vertical: Int): Boolean{
        if (cache.isNotEmpty() && cache.last() == horizontal to vertical)
            return false
        else {
            cache.add(horizontal to vertical)
        }
        if (cache.size >= 2) {
            makeTurn()
        }
        return true
    }

}
