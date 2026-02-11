import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HumanGuessesGame class
 */
public class HumanGuessesGameTest {

    // most of these tests use configuration injection to inject values for
    // "target"

    //Dependency Injection (on HumanGuessesGame)
    @Test
    void testMakeGuess_TooLow() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(250);

        assertEquals(GuessResult.LOW, result);
    }

    //ensures that when a guess is too high, the game properly records that result
    @Test
    void testMakeGuess_TooHigh() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(750);

        assertEquals(GuessResult.HIGH, result);
    }

    // ensures that proper guesses are recorded
    @Test
    void testMakeGuess_Correct() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(500);

        assertEquals(GuessResult.CORRECT, result);
    }

    // checks the edge case where the target is 1
    @Test
    void testMakeGuess_EdgeCase_MinValue() {
        HumanGuessesGame game = new HumanGuessesGame(1);

        assertEquals(GuessResult.CORRECT, game.makeGuess(1));
        assertEquals(GuessResult.HIGH, game.makeGuess(2));
    }

    // checks the egde case when the target is 1000
    @Test
    void testMakeGuess_EdgeCase_MaxValue() {
        HumanGuessesGame game = new HumanGuessesGame(1000);

        assertEquals(GuessResult.CORRECT, game.makeGuess(1000));
        assertEquals(GuessResult.LOW, game.makeGuess(999));
    }

    //checks edge case where target is one higher than the guess
    @Test
    void testMakeGuess_OneOffLow() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(499);

        assertEquals(GuessResult.LOW, result);
    }

    //same but when target is lower
    @Test
    void testMakeGuess_OneOffHigh() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        GuessResult result = game.makeGuess(501);

        assertEquals(GuessResult.HIGH, result);
    }


    // tests that numGuesses is properly initialized
    @Test
    void testGetNumGuesses_InitiallyZero() {
        HumanGuessesGame game = new HumanGuessesGame(500);

        assertEquals(0, game.getNumGuesses());
    }

    //tests that numguesses is incremented
    @Test
    void testGetNumGuesses_AfterOneGuess() {
        HumanGuessesGame game = new HumanGuessesGame(500);
        game.makeGuess(250);

        assertEquals(1, game.getNumGuesses());
    }

    //tests that numguesses is incremented consistently
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

    //tests that correct and incorrect guesses are counted properly
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
        //game doesn't end properly when a number is guessed correctly

        HumanGuessesGame game = new HumanGuessesGame(500);
        game.makeGuess(500);

        assertTrue(game.isDone());
    }

    //test a full binary search scenario of the game logic
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

    //edge case for when the game makes a correct guess on the first try
    @Test
    void testGame_GuessOnFirstTry() {
        HumanGuessesGame game = new HumanGuessesGame(42);

        GuessResult result = game.makeGuess(42);

        assertEquals(GuessResult.CORRECT, result);
        assertEquals(1, game.getNumGuesses());
    }

    //tests that the upper bound of the game is configured properly
    @Test
    void testUpperBound_Value() {
        assertEquals(1000, HumanGuessesGame.UPPER_BOUND);
    }


















}

