fun main(args: Array<String>){
    val checkersModel = CheckersModel()
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.WHITE, Pair(2, 2), Pair(3, 3)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.BLACK, Pair(5, 5), Pair(4, 4)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.WHITE, Pair(3, 3), Pair(5, 5)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.BLACK, Pair(5, 1), Pair(4, 0)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.WHITE, Pair(1, 1), Pair(2, 2)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.BLACK, Pair(6, 2), Pair(5, 1)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.WHITE, Pair(2, 2), Pair(3, 3)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.BLACK, Pair(7, 3), Pair(6, 2)))
    checkersModel.printBoardOnConsole()
    checkersModel.move(BaseTurn(Color.WHITE, Pair(5, 5), Pair(7, 3)))
    checkersModel.printBoardOnConsole()
}
/*2 2 3 3
5 5 4 4
3 3 5 5
5 1 4 0
1 1 2 2
6 2 5 1
2 2 3 3
7 3 6 2
5 5 7 3

 */