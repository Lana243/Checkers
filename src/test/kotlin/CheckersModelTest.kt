import org.junit.jupiter.api.Test
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class TestCanMove {

    @Test
    fun `Test Move of Ordinary Checker 1`() {
        val m = CheckersModel()
        assertNull(m.canMove(BaseTurn(1, Pair(0, 0), Pair(1, 1))),"Moved into not empty square")
        assertNull(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(4, 1))), "Wrong player made turn")
        assertSame(m.canMove(BaseTurn(1, Pair(2, 2), Pair(3, 3))), m.board[2, 2],
            "Wrong square returned: start expected")

        var movedChecker = m.board[2, 2].figure
        m.move(BaseTurn(1, Pair(2, 2), Pair(3, 3)))
        assertNull(m.board[2, 2].figure, "The start square is not empty after turn")
        assertSame(m.board[3, 3].figure, movedChecker, "The checker did not reach the finish square")
        assertNull(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(4, -1))), "Left the board")
        assertNull(m.canMove(BaseTurn(1, Pair(2, 7), Pair(3, 6))), "Wrong player made turn")
        assertNull(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(3, 2))),
            "The step was too long but checker is ordinary")
        assertNull(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(4, 0))), "The step is not diagonal")
        assertSame(m.canMove(BaseTurn(-1, Pair(5, 5), Pair(4, 4))), m.board[5, 5],
            "Wrong square returned: start square was excepted")

        movedChecker = m.board[5, 5].figure
        m.move(BaseTurn(-1, Pair(5, 5), Pair(4, 4)))
        assertNull(m.board[5, 5].figure, "The start square is not empty after turn")
        assertSame(m.board[4, 4].figure, movedChecker, "The checker did not reach the finish square")
        assertNull(m.canMove(BaseTurn(1, Pair(3, 3), Pair(2, 2))),
            "Ordinary checker cannot move back")
        assertNull(m.canMove(BaseTurn(1, Pair(3, 0), Pair(4, 1))), "Start square was empty")
        assertNull(m.canMove(BaseTurn(1, Pair(3, 3), Pair(4, 4))), "End square was not empty")
        assertSame(m.canMove(BaseTurn(1, Pair(3, 3), Pair(5, 5))), m.board[4, 4],
            "Wrong square returned: eaten checker expected")
        assertNull(m.canMove(BaseTurn(1, Pair(5, 0), Pair(4, 1))), "Player moved wrong checker")
    }
}