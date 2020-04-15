class ConsoleUIPlayer(private val name : String, col : Int) : BasePlayer(col) {
    override fun makeTurn(model : BaseModel): BaseTurn {
        println("Player $name - it's your turn.")
        while (true) {
            val input = readLine() ?: ""
            try {
                val (x1, y1, x2, y2) = input.split(' ')
                return BaseTurn(color, Pair(x1.toInt(), y1.toInt()), Pair(x2.toInt(), y2.toInt()))
            } catch (e: Throwable) {
                println("Invalid input. Please, try again")
            }
        }
    }
}