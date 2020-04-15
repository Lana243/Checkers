abstract class BaseModel() {
    abstract fun canMove(turn: BaseTurn) : Square?
    abstract fun move(turn: BaseTurn)
    abstract fun updateState()

    var gameState = GameState.PLAYING
    var whoMoves = 1
}