import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class TestCanMove {

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

    @Test
    fun `Test Color class`() {
        val black = Color.BLACK
        val white = Color.WHITE
        assertSame(black.getDirection(), -1, "Wrong result in getDirection() for black")
        assertSame(white.getDirection(), 1, "Wrong result in getDirection() for white")
        assertSame(black.nextColor(), Color.WHITE, "Wrong result in nextColor() for black")
        assertSame(white.nextColor(), Color.BLACK, "Wrong result in nextColor() for white")
        assertSame(black.queenLine(8), 0, "Wrong queen line for black")
        assertSame(black.queenLine(2), 0, "Wrong queen line for black")
        assertSame(white.queenLine(8), 7, "Wrong queen line for white")
        assertSame(white.queenLine(2), 1, "Wrong queen line for white")
    }
}