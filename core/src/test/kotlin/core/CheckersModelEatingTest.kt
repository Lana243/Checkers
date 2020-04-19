package core

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class TestEating {
    data class TestSet(
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
            listOf(0 to 0, 0 to 2, 0 to 6, 1 to 1, 1 to 7, 2 to 4, 3 to 7, 4 to 2),
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
            DynamicTest.dynamicTest("Test on $it") {
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
            DynamicTest.dynamicTest("Test on $it") {
                it.model.whoMoves = Color.WHITE
                assertEquals(it.model.canEat(), it.whiteCanEatList.isNotEmpty(), "Method canEat() works incorrectly")
                it.model.whoMoves = Color.BLACK
                assertEquals(it.model.canEat(), it.blackCanEatList.isNotEmpty(), "Method canEat() works incorrectly")
            }
        }.toList()
    }

    @Test
    fun `Test CheckersModel ban to eat twice`() {
        var testBoard = TestSet(
                listOf(5 to 5),
                listOf(1 to 1, 3 to 3, 6 to 6),
                emptyList(),
                emptyList())
        assertSame(testBoard.model.canMove(BaseTurn(Color.WHITE, 5 to 5, 7 to 7)), testBoard.model.board[6, 6])
        testBoard.model.move(BaseTurn(Color.WHITE, 5 to 5, 7 to 7))
        assertNull(testBoard.model.canMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 3)))
        assertEquals(testBoard.model.whoMoves, Color.BLACK)
        assertSame(testBoard.model.canMove(BaseTurn(Color.BLACK, 1 to 1, 0 to 0)), testBoard.model.board[1, 1])
        testBoard.model.move(BaseTurn(Color.BLACK, 1 to 1, 0 to 0))
        assertEquals(testBoard.model.board[7, 7].figure!!.type, FigureType.Queen)
        assertNull(testBoard.model.canMove(BaseTurn(Color.WHITE, 7 to 7, 3 to 3)))
    }

}
