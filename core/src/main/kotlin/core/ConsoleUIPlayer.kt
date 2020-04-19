package core

enum class InputType {
    INDEXING_0,
    STANDARD_CHESSBOARD_NOTATION
}

class ConsoleUIPlayer(private val name : String,
                      col : Color,
                      private val inputType: InputType = InputType.STANDARD_CHESSBOARD_NOTATION) : BasePlayer<CheckersModel>(col) {
    override fun makeTurn(model : CheckersModel): BaseTurn {
        println("Player $name - it's your turn.")
        while (true) {
            try {
                val turn = readTurn()
                return turn!!
            } catch (e: Throwable) {
                println("Invalid input. Please, try again")
            }
        }
    }

    private fun readTurn(): BaseTurn {
        when (inputType) {
            InputType.INDEXING_0 -> {
                val input = readLine() ?: ""
                val (x1, y1, x2, y2) = input.split(' ')
                return BaseTurn(color, Pair(x1.toInt(), y1.toInt()), Pair(x2.toInt(), y2.toInt()))
            }
            InputType.STANDARD_CHESSBOARD_NOTATION -> {
                val input = (readLine() ?: "").split(Regex("""\s+"""))
                val x1 = input[0][0].toInt() - 'a'.toInt()
                val y1 = input[0][1].toInt() - '1'.toInt()
                val x2 = input[1][0].toInt() - 'a'.toInt()
                val y2 = input[1][1].toInt() - '1'.toInt()
                return BaseTurn(color, Pair(x1, y1), Pair(x2, y2))
            }
        }
        throw Exception("Wrong turn")
    }
}