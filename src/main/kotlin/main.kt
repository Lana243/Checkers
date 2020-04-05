fun main(args: Array<String>){
    val checkersModel = CheckersModel()
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.WHITE, Pair(2, 2), Pair(3, 3)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.BLACK, Pair(5, 5), Pair(4, 4)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.WHITE, Pair(3, 3), Pair(4, 4)))
    checkersModel.printBoardOnConsole()
}