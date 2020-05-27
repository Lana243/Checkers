package ml

fun main(args: Array<String>){
    /*val model = CheckersModel()
    val e = PositionEvaluation(model, Color.WHITE)
    print("${e.checkers} ${e.queens} ${e.mainDiagQueen} " +
            "${e.averageVertical} ${e.averageHorizontal}  ${e.safeCheckers} ${e.twoDiag} " +
            "${e.threeDiag} ${e.triangles} ${e.flippedTriangles} ${e.c5_f4} ${e.e5_d4} " +
            "${e.a7_h2} ${e.a3_h6} ${e.h4_a5}")*/
    //GeneticTesting().test()
    GeneticTeaching().start()
}

