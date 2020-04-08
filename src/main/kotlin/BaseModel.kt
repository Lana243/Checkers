abstract class BaseModel(val boardSize: Int) {
    abstract fun canMove(turn: BaseTurn) : Square?
    abstract fun move(turn: BaseTurn)
    abstract fun updateState()

    val board = Array(boardSize) {Array(boardSize) {Square(1) } }
    var gameState = GameState.PLAYING

    abstract fun printBoardOnConsole()
}