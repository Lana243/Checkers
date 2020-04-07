class ConsoleUIPlayer(private val name : String, col : Color) : BasePlayer(col) {
    override fun makeTurn(model : BaseModel): BaseTurn? {
        println("Player $name - it's your turn.")
        val input = readLine()
        if (input == null)
            return null
        else {
            val (x1, y1, x2, y2) = input.split(' ')
            return BaseTurn(color, Pair(x1.toInt(), y1.toInt()), Pair(x2.toInt(), y2.toInt()))
        }
    }
}