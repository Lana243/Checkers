package core

abstract class BasePlayer<ModelT : BaseModel>(val color : Color) {
    lateinit var baseGame: BaseGame
    abstract fun makeTurn()
    abstract fun update()
    abstract fun illegalTurn()
    fun setGame(game: BaseGame) {
        this.baseGame = game
    }
}