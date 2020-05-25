package ml

import core.CheckersModel
import core.Color

fun main(args: Array<String>){
    val model = CheckersModel()
    val eval = PositionEvaluation(model, Color.WHITE)

}

