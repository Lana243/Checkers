import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.*

class TestEating {
    data class TestSet (
            val whiteList: List<Pair<Int, Int>>,
            val blackList: List<Pair<Int, Int>>,
            val whiteCanEatList: List<Pair<Int, Int>>,
            val blackCanEatList: List<Pair<Int, Int>>) {
        //val board = CheckersBoard(8)
        val model = CheckersModel()
        init {
            model.board.setEmptyPosition()
            for (white in whiteList) {
                model.board[white.first, white.second].figure = Figure(Color.WHITE)
            }
            for (black in blackList) {
                model.board[black.first, black.second].figure = Figure(Color.BLACK)
            }
        }
    }

    private val withOutQueens5 = TestSet(
            listOf(0 to 0, 2 to 2, 4 to 4, 6 to 6),
            listOf(1 to 1, 3 to 3, 5 to 5, 7 to 7),
            emptyList(),
            emptyList()
    )
    private val withOutQueens4 = TestSet(
            listOf(0 to 0, 0 to 2, 2 to 4, 2 to 6, 3 to 3, 3 to 7),
            listOf(1 to 1, 1 to 5, 2 to 2, 4 to 4, 5 to 1, 5 to 3, 6 to 0, 6 to 4, 6 to 6, 7 to 1),
            listOf(0 to 2, 2 to 4, 2 to 6, 3 to 3),
            emptyList()
    )
    private val withOutQueens3 = TestSet(
            listOf(0 to 0, 0 to 2, 0 to 6, 1 to 1, 1 to 7, 2 to 4, 3 to 7,4 to 2),
            listOf(1 to 3, 1 to 5),
            emptyList(),
            listOf(1 to 3, 1 to 5)
    )
    private val withOutQueens1 = TestSet(
            listOf(1 to 1, 0 to 2, 0 to 4, 0 to 6, 1 to 7, 2 to 4, 3 to 3, 5 to 7, 6 to 0, 4 to 0, 6 to 4),
            listOf(1 to 3, 2 to 2, 3 to 1, 3 to 7, 4 to 6, 5 to 1, 7 to 5, 7 to 7),
            listOf(4 to 0, 6 to 0, 5 to 7),
            listOf(2 to 2, 1 to 3, 7 to 5)
    )
    private val withOutQueens2 = TestSet(
            listOf(0 to 0, 0 to 4, 0 to 6, 1 to 1, 1 to 7, 3 to 3, 3 to 5, 5 to 7, 6 to 6),
            listOf(1 to 3, 2 to 6, 3 to 1, 4 to 2, 4 to 6, 6 to 2, 6 to 4, 7 to 1, 7 to 5, 7 to 7),
            listOf(0 to 4, 3 to 3),
            listOf(2 to 6, 4 to 6, 4 to 2, 7 to 7)
    )

    private val testListWithOutQueen = listOf(
            withOutQueens1,
            withOutQueens2,
            withOutQueens3,
            withOutQueens4,
            withOutQueens5
    )

    @TestFactory
    fun `Test CheckersBoard getCoords() method`() : Collection<DynamicTest> {

        return testListWithOutQueen.map {
            dynamicTest("Test on $it") {
                val whiteCoords = it.model.board.getCoords(Color.WHITE)
                val blackCoords = it.model.board.getCoords(Color.BLACK)
                assertTrue("Not all white checkers were found") { whiteCoords.containsAll(it.whiteList) }
                assertTrue("Some excess white checkers were found") { it.whiteList.containsAll(whiteCoords) }
                assertEquals(it.whiteList.size, whiteCoords.size, "White lists have not equal sizes")
                assertTrue("Not all black checkers were found") { blackCoords.containsAll(it.blackList) }
                assertTrue("Some excess black checkers were found") { it.blackList.containsAll(blackCoords) }
                assertEquals(it.blackList.size, blackCoords.size, "Black lists have not equal sizes")
            }
        }.toList()
    }

    @TestFactory
    fun `Test CheckersModel canEat() method`() : Collection<DynamicTest> {
        return testListWithOutQueen.map {
            dynamicTest("Test on $it") {
                it.model.whoMoves = Color.WHITE
                assertEquals(it.model.canEat(), it.whiteCanEatList.isNotEmpty(), "Method canEat() works incorrectly")
                it.model.whoMoves = Color.BLACK
                assertEquals(it.model.canEat(), it.blackCanEatList.isNotEmpty(), "Method canEat() works incorrectly")
            }
        }.toList()
    }
}

class TestTurnClassMethods {
    @Test
    fun `Test isValid Function for Turn`() {
        val turn1 = BaseTurn(Color.WHITE, 0 to 0, 2 to 2)
        assertFalse(turn1.isValid(2), "isValid while 'to' coords are outside the board")
        assertTrue(turn1.isValid(3), "!isValid while all coords are inside the board")
        assertTrue(turn1.isValid(5),"!isValid while all coords are inside the board")
        val turn2 = BaseTurn(Color.BLACK, 2 to 2, 0 to 0)
        assertFalse(turn2.isValid(2), "isValid while 'from' coords are outside the board")
        assertTrue(turn2.isValid(3), "!isValid while all coords are inside the board")
        assertTrue(turn2.isValid(5),"!isValid while all coords are inside the board")
        val turn3 = BaseTurn(Color.WHITE, 6 to 5, 3 to 6)
        assertFalse(turn3.isValid(6), "isValid while 'from' and 'to' coords are outside the board")
        assertFalse(turn3.isValid(2), "isValid while 'from' and 'to' coords are outside the board")
        assertTrue(turn2.isValid(10), "!isValid while all coords are inside the board")
        assertTrue(turn2.isValid(7),"!isValid while all coords are inside the board")
    }
}

class TestColorClass {
    private val black = Color.BLACK
    private val white = Color.WHITE

    @Test
    fun `Test getDirection method`() {
        assertEquals(black.getDirection(), -1, "Wrong result in getDirection() for black")
        assertEquals(white.getDirection(), 1, "Wrong result in getDirection() for white")
    }

    @Test
    fun `Test nextColor method`() {
        assertEquals(black.nextColor(), Color.WHITE, "Wrong result in nextColor() for black")
        assertEquals(white.nextColor(), Color.BLACK, "Wrong result in nextColor() for white")
    }

    @Test
    fun `Test queenLine method`() {
        assertEquals(black.queenLine(8), 0, "Wrong queen line for black")
        assertEquals(black.queenLine(2), 0, "Wrong queen line for black")
        assertEquals(white.queenLine(8), 7, "Wrong queen line for white")
        assertEquals(white.queenLine(2), 1, "Wrong queen line for white")
    }
}

class TestCanMove {

    @Test
    fun `Test Move of Ordinary Checker 1`() {
        val m = CheckersModel()
        assertNull(m.canMove(BaseTurn(Color.WHITE, Pair(0, 0), Pair(1, 1))),"Moved into not empty square")
        assertNull(m.canMove(BaseTurn(Color.BLACK, Pair(5, 0), Pair(4, 1))), "Wrong player made turn")
        assertSame(m.canMove(BaseTurn(Color.WHITE, Pair(2, 2), Pair(3, 3))), m.board[2, 2],
            "Wrong square returned: start expected")

        var movedChecker = m.board[2, 2].figure
        m.move(BaseTurn(Color.WHITE, Pair(2, 2), Pair(3, 3)))
        assertNull(m.board[2, 2].figure, "The start square is not empty after turn")
        assertSame(m.board[3, 3].figure, movedChecker, "The checker did not reach the finish square")
        assertNull(m.canMove(BaseTurn(Color.BLACK, Pair(5, 0), Pair(4, -1))), "Left the board")
        assertNull(m.canMove(BaseTurn(Color.WHITE, Pair(2, 7), Pair(3, 6))), "Wrong player made turn")
        assertNull(m.canMove(BaseTurn(Color.BLACK, Pair(5, 0), Pair(3, 2))),
            "The step was too long but checker is ordinary")
        assertNull(m.canMove(BaseTurn(Color.BLACK, Pair(5, 0), Pair(4, 0))), "The step is not diagonal")
        assertSame(m.canMove(BaseTurn(Color.BLACK, Pair(5, 5), Pair(4, 4))), m.board[5, 5],
            "Wrong square returned: start square was excepted")

        movedChecker = m.board[5, 5].figure
        m.move(BaseTurn(Color.BLACK, Pair(5, 5), Pair(4, 4)))
        assertNull(m.board[5, 5].figure, "The start square is not empty after turn")
        assertSame(m.board[4, 4].figure, movedChecker, "The checker did not reach the finish square")
        assertNull(m.canMove(BaseTurn(Color.WHITE, Pair(3, 3), Pair(2, 2))),
            "Ordinary checker cannot move back")
        assertNull(m.canMove(BaseTurn(Color.WHITE, Pair(3, 0), Pair(4, 1))), "Start square was empty")
        assertNull(m.canMove(BaseTurn(Color.WHITE, Pair(3, 3), Pair(4, 4))), "End square was not empty")
        assertSame(m.canMove(BaseTurn(Color.WHITE, Pair(3, 3), Pair(5, 5))), m.board[4, 4],
            "Wrong square returned: eaten checker expected")
        assertNull(m.canMove(BaseTurn(Color.WHITE, Pair(5, 0), Pair(4, 1))), "Player moved wrong checker")
    }
}

class CommonLogicTest {

    private val board = CheckersBoard(8)
    private val model = CheckersModel()

    private fun wrongMove(turn: BaseTurn) {
        assertNull(model.canMove(turn), "Can make turn when it is wrong")
    }

    private fun rightMove(turn: BaseTurn) {
        assertNotNull(model.canMove(turn), "Can't make turn when it is right")
    }

    private fun makeMove(turn: BaseTurn, changeColor: Boolean) {
        rightMove(turn)
        model.move(turn)
        assertEquals(model.whoMoves, if (changeColor) turn.playerColor.nextColor() else turn.playerColor, "Wrong players'color changing")
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
        rightMove(BaseTurn(Color.BLACK, 6 to 0, 4 to 2))
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
        board[4 to 6].figure = null
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.BLACK, 6 to 4, 4 to 6))
        wrongMove(BaseTurn(Color.BLACK, 5 to 5, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 2 to 0, 3 to 1))
        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 3 to 7))
        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 4 to 6))
        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 4 to 4))
        makeMove(BaseTurn(Color.WHITE, 5 to 5, 3 to 3), true)
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
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 5 to 5, 3 to 7))
        makeMove(BaseTurn(Color.WHITE, 5 to 5, 7 to 7), true)
        board[6, 6].figure = null
        board[7, 7].figure!!.type = FigureType.Queen
        compareBoardAndModel()

        makeMove(BaseTurn(Color.BLACK, 7 to 1, 6 to 2), true)
        compareBoardAndModel()

        rightMove(BaseTurn(Color.WHITE, 7 to 7, 6 to 6))
        rightMove(BaseTurn(Color.WHITE, 7 to 7, 5 to 5))
        rightMove(BaseTurn(Color.WHITE, 7 to 7, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 2 to 2))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 1 to 1))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 0 to 0))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 5))
        makeMove(BaseTurn(Color.WHITE, 2 to 0, 3 to 1),true)
        compareBoardAndModel()

        makeMove(BaseTurn(Color.BLACK, 7 to 5, 6 to 6), true)
        compareBoardAndModel()

        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 6 to 6))
        rightMove(BaseTurn(Color.WHITE, 7 to 7, 5 to 5))
        rightMove(BaseTurn(Color.WHITE, 7 to 7, 4 to 4))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 3))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 2 to 2))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 1 to 1))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 0 to 0))
        wrongMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 5))
        wrongMove(BaseTurn(Color.WHITE, 3 to 1, 4 to 2))
        makeMove(BaseTurn(Color.WHITE, 7 to 7, 4 to 4), false)
        board[6, 6].figure = null

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
        board[6 to 2].figure = null
        compareBoardAndModel()

    }


}