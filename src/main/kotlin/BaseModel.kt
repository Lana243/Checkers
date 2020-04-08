abstract class BaseModel(val boardSize: Int, val board : BaseBoard) {
    abstract fun canMove(turn: BaseTurn) : Square?
    abstract fun move(turn: BaseTurn)
    abstract fun updateState()

    var gameState = GameState.PLAYING

}