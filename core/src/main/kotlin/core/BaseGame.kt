package core

interface BaseGame {
    fun makeTurn(turn: BaseTurn)
    fun getModel(): CheckersModel
}
