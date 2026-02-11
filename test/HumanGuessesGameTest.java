import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HumanGuessesGame class
 */
public class HumanGuessesGameTest {


    @Test
    void testMakeGuess_TooLow() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(250);

        assertEquals(GuessResult.LOW, result);
    }




    @Test
    void testMakeGuess_TooHigh() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(750);

        assertEquals(GuessResult.HIGH, result);
    }

    @Test
    void testMakeGuess_Correct() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(500);

        assertEquals(GuessResult.CORRECT, result);
    }

    @Test
    void testMakeGuess_EdgeCase_MinValue() {
        HumanGuessesGame game = new HumanGuessesGame(1);

        assertEquals(GuessResult.CORRECT, game.makeGuess(1));
        assertEquals(GuessResult.HIGH, game.makeGuess(2));
    }

    @Test
    void testMakeGuess_EdgeCase_MaxValue() {
        HumanGuessesGame game = new HumanGuessesGame(1000);

        assertEquals(GuessResult.CORRECT, game.makeGuess(1000));
        assertEquals(GuessResult.LOW, game.makeGuess(999));
    }

    @Test
    void testMakeGuess_OneOffLow() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(499);

        assertEquals(GuessResult.LOW, result);
    }

    @Test
    void testMakeGuess_OneOffHigh() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(501);

        assertEquals(GuessResult.HIGH, result);
    }



    @Test
    void testGetNumGuesses_InitiallyZero() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        assertEquals(0, game.getNumGuesses());
    }

    @Test
    void testGetNumGuesses_AfterOneGuess() {
        HumanGuessesGame game = new HumanGuessesGame(500);
        game.makeGuess(250);

        assertEquals(1, game.getNumGuesses());
    }

    @Test
    void testGetNumGuesses_AfterMultipleGuesses() {
        HumanGuessesGame game = new HumanGuessesGame(500);
        game.makeGuess(250);
        game.makeGuess(375);
        game.makeGuess(437);
        game.makeGuess(468);
        game.makeGuess(500);

        assertEquals(5, game.getNumGuesses());
    }

    @Test
    void testGetNumGuesses_CountsIncorrectAndCorrectGuesses() {
        HumanGuessesGame game = new HumanGuessesGame(500);
        game.makeGuess(100); // wrong
        game.makeGuess(900); // wrong
        game.makeGuess(500); // correct

        assertEquals(3, game.getNumGuesses());
    }

    // ========== Tests for isDone method ==========

    @Test
    void testIsDone_InitiallyFalse() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        assertFalse(game.isDone());
    }

    @Test
    void testIsDone_AfterIncorrectGuess() {
        HumanGuessesGame game = new HumanGuessesGame(500);
        game.makeGuess(250);

        assertFalse(game.isDone());
    }

    @Test
    void testIsDone_AfterCorrectGuess() {
        //i think this is a found bug

        HumanGuessesGame game = new HumanGuessesGame(500);
        game.makeGuess(500);

        assertTrue(game.isDone());
    }






    @Test
    void testFullGame_BinarySearchPattern() {
        HumanGuessesGame game = new HumanGuessesGame(750);

        // Simulate binary search
        assertEquals(GuessResult.LOW, game.makeGuess(500));
        assertEquals(GuessResult.HIGH, game.makeGuess(875));
        assertEquals(GuessResult.LOW, game.makeGuess(687));
        assertEquals(GuessResult.CORRECT, game.makeGuess(750));

        assertEquals(4, game.getNumGuesses());
    }

    @Test
    void testGame_GuessOnFirstTry() {
        HumanGuessesGame game = new HumanGuessesGame(42);

        GuessResult result = game.makeGuess(42);

        assertEquals(GuessResult.CORRECT, result);
        assertEquals(1, game.getNumGuesses());
    }


    @Test
    void testUpperBound_Value() {
        assertEquals(1000, HumanGuessesGame.UPPER_BOUND);
    }


















}

