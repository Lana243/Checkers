import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TestCanMove {

    @Test
    fun `Test Move of Ordinary Checker 1`() {
        var m = CheckersModel()
        assertTrue(m.canMove(BaseTurn(-1, Pair(0, 0), Pair(1, 1))) == null, "Moved into not empty square")
        assertTrue(m.canMove(BaseTurn(1, Pair(5, 0), Pair(4, 1))) == null, "Wrong player made turn")

    }
}