import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class TestCanMove {

    @Test
    fun `Test Move of Ordinary Checker 1`() {
        val m = CheckersModel()
        assertTrue(m.canMove(BaseTurn(1, Pair(0, 0), Pair(1, 1))) == null,
            "Moved into not empty square")
        assertTrue(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(4, 1))) == null,
            "Wrong player made turn")
        assertTrue(m.canMove(BaseTurn(1, Pair(2, 2), Pair(3, 3))) === m.board[2][2],
            "Wrong square returned: start excepted")

        m.move(BaseTurn(1, Pair(2, 2), Pair(3, 3)))
        assertTrue(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(4, -1))) == null,
            "Left the board")
        assertTrue(m.canMove(BaseTurn(1, Pair(2, 7), Pair(3, 6))) == null,
            "Wrong player made turn")
        assertTrue(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(3, 2))) == null,
            "The step was too long but checker is ordinary")
        assertTrue(m.canMove(BaseTurn(-1, Pair(5, 0), Pair(4, 0))) == null,
            "The step is not diagonal")
        assertTrue(m.canMove(BaseTurn(-1, Pair(5, 5), Pair(4, 4))) === m.board[5][5],
            "Wrong square returned: start square was excepted")

        m.move(BaseTurn(-1, Pair(5, 5), Pair(4, 4)))
        assertTrue(m.canMove(BaseTurn(1, Pair(3, 3), Pair(2, 2))) == null,
            "Ordinary checker cannot move back")
        assertTrue(m.canMove(BaseTurn(1, Pair(3, 0), Pair(4, 1))) == null,
            "Start square was empty")
        assertTrue(m.canMove(BaseTurn(1, Pair(3, 3), Pair(4, 4))) == null,
            "End square was not empty")
        assertTrue(m.canMove(BaseTurn(1, Pair(3, 3), Pair(5, 5))) === m.board[4][4],
            "Wrong square returned: felled excepted")
        assertTrue(m.canMove(BaseTurn(1, Pair(5, 0), Pair(4, 1))) == null,
            "Player moved wrong checker")
    }
}