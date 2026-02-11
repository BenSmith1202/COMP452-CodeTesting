import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class GameResultTest {

    // constructor

    @Test
    void testConstructor_HumanPlaying() {
        GameResult result = new GameResult(true, 500, 10);

        assertTrue(result.humanWasPlaying);
        assertEquals(500, result.correctValue);
        assertEquals(10, result.numGuesses);
    }

    @Test
    void testConstructor_ComputerPlaying() {
        GameResult result = new GameResult(false, 750, 8);

        assertFalse(result.humanWasPlaying);
        assertEquals(750, result.correctValue);
        assertEquals(8, result.numGuesses);
    }




    @Test
    void testConstructor_MinValues() {
        GameResult result = new GameResult(true, 1, 1);

        assertTrue(result.humanWasPlaying);
        assertEquals(1, result.correctValue);
        assertEquals(1, result.numGuesses);
    }

    @Test
    void testConstructor_MaxValues() {
        GameResult result = new GameResult(true, 1000, 100);

        assertTrue(result.humanWasPlaying);
        assertEquals(1000, result.correctValue);
        assertEquals(100, result.numGuesses);
    }

    // edge cases (spooky)

    @Test
    void testConstructor_ZeroGuesses() {
        GameResult result = new GameResult(true, 500, 0);

        assertEquals(0, result.numGuesses);
    }

    @Test
    void testConstructor_ZeroCorrectValue() {
        GameResult result = new GameResult(true, 0, 5);

        assertEquals(0, result.correctValue);
    }

    @Test
    void testConstructor_NegativeValues() {
        GameResult result = new GameResult(false, -100, -5);

        assertEquals(-100, result.correctValue);
        assertEquals(-5, result.numGuesses);
    }







    @Test
    void testConstructor_HumanWins_LowValue() {
        GameResult result = new GameResult(true, 42, 7);

        assertTrue(result.humanWasPlaying);
        assertEquals(42, result.correctValue);
        assertEquals(7, result.numGuesses);
    }

    @Test
    void testConstructor_ComputerWins_HighValue() {
        GameResult result = new GameResult(false, 999, 12);

        assertFalse(result.humanWasPlaying);
        assertEquals(999, result.correctValue);
        assertEquals(12, result.numGuesses);
    }

    @Test
    void testConstructor_FirstTryGuess() {
        GameResult result = new GameResult(true, 501, 1);

        assertTrue(result.humanWasPlaying);
        assertEquals(501, result.correctValue);
        assertEquals(1, result.numGuesses);
    }




    @Test
    void testConstructor_LargeNumberOfGuesses() {
        GameResult result = new GameResult(true, 500, 1000);

        assertEquals(1000, result.numGuesses);
    }











}

