abstract class BaseModel(open val boardSize: Int) {
    abstract fun canMove(turn: BaseTurn) : Pair<Boolean, Any?>
    abstract fun move(turn: BaseTurn)
    abstract fun updateState()

    val board = Array(boardSize) {Array(boardSize) {Square(false) } }
    var gameState = GameState.PLAYING

    abstract fun printBoardOnConsole()
}