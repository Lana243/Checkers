package core

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame

class CommonLogicTest {

    private val board = CheckersBoard(8)
    private val model = CheckersModel()

    private fun wrongMove(turn: BaseTurn) {
        assertNull(model.canMove(turn), "Can make turn when it is wrong")
    }

    private fun correctMove(turn: BaseTurn) {
        assertNotNull(model.canMove(turn), "Can't make turn when it is right")
    }

    private fun makeMove(turn: BaseTurn, changeColor: Boolean) {
        correctMove(turn)
        model.move(turn)
        assertEquals(
                model.whoMoves,
                if (changeColor) turn.playerColor.nextColor() else turn.playerColor,
                "Wrong players'color changing"
        )
        board[turn.to].figure = board[turn.from].figure
        board[turn.from].figure = null
    }

    private fun compareBoardAndModel() {
        assertEquals(board, model.board, "Board state isn't right")
    }


    @Test
    fun `Common Logic Test`() {
        board.setStartPosition()
        assertSame(model.whoMoves, Color.WHITE)
        compareBoardAndModel()
        wrongMove(BaseTurn(Color.BLACK, 5 to 5, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 2 to 2, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 2 to 2, 3 to 2))
        wrongMove(BaseTurn(Color.WHITE, 2 to 2, 2 to 3))
        makeMove(BaseTurn(Color.WHITE, 2 to 2, 3 to 3), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 3 to 3, 4 to 4))
        wrongMove(BaseTurn(Color.BLACK, 3 to 3, 4 to 4))
        wrongMove(BaseTurn(Color.BLACK, 3 to 3, 2 to 2))
        wrongMove(BaseTurn(Color.BLACK, 0 to 4, 1 to 3))
        makeMove(BaseTurn(Color.BLACK, 5 to 1, 4 to 2), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 3 to 3, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 2 to 4, 3 to 5))
        wrongMove(BaseTurn(Color.BLACK, 3 to 3, 5 to 1))
        wrongMove(BaseTurn(Color.WHITE, 5 to 1, 3 to 3))
        makeMove(BaseTurn(Color.WHITE, 3 to 3, 5 to 1), true)
        board[4, 2].figure = null
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 2 to 4, 3 to 5))
        wrongMove(BaseTurn(Color.BLACK, 5 to 1, 4 to 2))
        wrongMove(BaseTurn(Color.BLACK, 6 to 0, 4 to 0))
        wrongMove(BaseTurn(Color.BLACK, 6 to 2, 4 to 2))
        correctMove(BaseTurn(Color.BLACK, 6 to 0, 4 to 2))
        wrongMove(BaseTurn(Color.BLACK, 5 to 3, 4 to 2))
        makeMove(BaseTurn(Color.BLACK, 6 to 2, 4 to 0), true)
        board[5, 1].figure = null
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.BLACK, 4 to 0, 3 to 1))
        wrongMove(BaseTurn(Color.WHITE, 2 to 2, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 0 to 0, 2 to 2))
        wrongMove(BaseTurn(Color.WHITE, 0 to 0, 1 to 1))
        makeMove(BaseTurn(Color.WHITE, 2 to 6, 3 to 7), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.BLACK, 6 to 4, 4 to 6))
        makeMove(BaseTurn(Color.BLACK, 5 to 3, 4 to 4), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.BLACK, 4 to 2, 5 to 3))
        wrongMove(BaseTurn(Color.WHITE, 4 to 2, 5 to 3))
        wrongMove(BaseTurn(Color.BLACK, 5 to 3, 4 to 2))
        makeMove(BaseTurn(Color.WHITE, 1 to 1, 2 to 2), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.BLACK, 2 to 2, 1 to 1))
        wrongMove(BaseTurn(Color.BLACK, 5 to 5, 6 to 6))
        wrongMove(BaseTurn(Color.BLACK, 4 to 4, 5 to 3))
        makeMove(BaseTurn(Color.BLACK, 5 to 5, 4 to 6), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 2 to 2, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 2 to 2, 5 to 5))
        wrongMove(BaseTurn(Color.WHITE, 3 to 7, 4 to 6))
        makeMove(BaseTurn(Color.WHITE, 3 to 7, 5 to 5), false)

        wrongMove(BaseTurn(Color.BLACK, 6 to 4, 4 to 6))
        wrongMove(BaseTurn(Color.BLACK, 5 to 5, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 2 to 0, 3 to 1))
        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 3 to 7))
        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 4 to 6))
        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 4 to 4))
        makeMove(BaseTurn(Color.WHITE, 5 to 5, 3 to 3), true)
        board[4 to 6].figure = null
        board[4, 4].figure = null
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.BLACK, 4 to 4, 3 to 5))
        makeMove(BaseTurn(Color.BLACK, 6 to 6, 5 to 5), true)
        compareBoardAndModel()

        makeMove(BaseTurn(Color.WHITE, 1 to 5, 2 to 6), true)
        compareBoardAndModel()

        makeMove(BaseTurn(Color.BLACK, 5 to 5, 4 to 6), true)
        compareBoardAndModel()

        makeMove(BaseTurn(Color.WHITE, 2 to 6, 3 to 7), true)
        compareBoardAndModel()

        makeMove(BaseTurn(Color.BLACK, 7 to 7, 6 to 6), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 0 to 4, 1 to 5))
        wrongMove(BaseTurn(Color.WHITE, 3 to 7, 7 to 7))
        wrongMove(BaseTurn(Color.WHITE, 3 to 7, 4 to 6))
        makeMove(BaseTurn(Color.WHITE, 3 to 7, 5 to 5), false)
        board[4, 6].figure = null

        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 3 to 7))
        makeMove(BaseTurn(Color.WHITE, 5 to 5, 7 to 7), true)
        board[6, 6].figure = null
        board[7, 7].figure!!.type = FigureType.Queen
        compareBoardAndModel()

        makeMove(BaseTurn(Color.BLACK, 7 to 1, 6 to 2), true)
        compareBoardAndModel()

        correctMove(BaseTurn(Color.WHITE, 7 to 7, 6 to 6))
        correctMove(BaseTurn(Color.WHITE, 7 to 7, 5 to 5))
        correctMove(BaseTurn(Color.WHITE, 7 to 7, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 2 to 2))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 1 to 1))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 0 to 0))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 5))
        makeMove(BaseTurn(Color.WHITE, 2 to 0, 3 to 1), true)
        compareBoardAndModel()

        makeMove(BaseTurn(Color.BLACK, 7 to 5, 6 to 6), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 6 to 6))
        correctMove(BaseTurn(Color.WHITE, 7 to 7, 5 to 5))
        correctMove(BaseTurn(Color.WHITE, 7 to 7, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 2 to 2))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 1 to 1))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 0 to 0))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 5))
        wrongMove(BaseTurn(Color.WHITE, 3 to 1, 4 to 2))
        makeMove(BaseTurn(Color.WHITE, 7 to 7, 4 to 4), false)

        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 5 to 5))
        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 6 to 6))
        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 7 to 7))
        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 5 to 3))
        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 6 to 2))
        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 3 to 5))
        wrongMove(BaseTurn(Color.WHITE, 4 to 4, 2 to 6))
        wrongMove(BaseTurn(Color.WHITE, 3 to 1, 4 to 2))
        makeMove(BaseTurn(Color.WHITE, 4 to 4, 7 to 1), true)
        board[6, 6].figure = null
        board[6 to 2].figure = null
        compareBoardAndModel()

    }
}
