abstract class BaseModel(open val boardSize: Int) {
    abstract fun canMove(turn: BaseTurn) : Boolean
    abstract fun move(turn: BaseTurn)
    abstract fun isEnded() : Boolean

    val board = Array(boardSize) {Array(boardSize) {Square(false) } }

    abstract fun printBoardOnConsole()
}