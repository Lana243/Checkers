class ConsoleUIPlayer(private val name : String,
                      col : Color,
                      private val inputType: Int = 0) : BasePlayer(col) {
    override fun makeTurn(model : BaseModel): BaseTurn {
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

    private fun readTurn(): BaseTurn? {
        when (inputType) {
            0 -> {
                val input = readLine() ?: ""
                val (x1, y1, x2, y2) = input.split(' ')
                return BaseTurn(color, Pair(x1.toInt(), y1.toInt()), Pair(x2.toInt(), y2.toInt()))
            }
            1 -> {
                val input = (readLine() ?: "").split(Regex("""\s+"""))
                val x1 = input[0][0].toInt() - 'a'.toInt()
                val y1 = input[0][1].toInt() - '1'.toInt()
                val x2 = input[1][0].toInt() - 'a'.toInt()
                val y2 = input[1][1].toInt() - '1'.toInt()
                return BaseTurn(color, Pair(x1, y1), Pair(x2, y2))
            }
        }
        return null
    }
}