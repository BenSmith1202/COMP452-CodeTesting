import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for ComputerGuessesGame class
 */
public class ComputerGuessesGameTest {

    private ComputerGuessesGame game;

    @BeforeEach
    void setUp() {
        game = new ComputerGuessesGame();
    }

    // basic tests

    @Test
    void testReset_ReturnsMiddleValue() {
        int firstGuess = game.reset();

        assertEquals(501, firstGuess);
    }

    @Test
    void testReset_NumGuessesIsZero() {
        game.reset();

        assertEquals(0, game.getNumGuesses());
    }

    @Test
    void testConstructor_FirstGuessIsCorrect() {
        assertEquals(501, game.getLastGuess());
    }

    @Test
    void testConstructor_NumGuessesIsZero() {
        assertEquals(0, game.getNumGuesses());
    }




    @Test
    void testRecordLower_UpdatesGuess() {
        game.reset(); // lastGuess 501

        int newGuess = game.recordLower();

        assertEquals(251, newGuess);
    }

    @Test
    void testRecordLower_IncrementsNumGuesses() {
        game.reset();
        game.recordLower();

        assertEquals(1, game.getNumGuesses());
    }



    @Test
    void testRecordHigher_UpdatesGuess() {
        game.reset(); // lastGuess = 501

        int newGuess = game.recordHigher();

        assertEquals(751, newGuess);
    }

    @Test
    void testRecordHigher_IncrementsNumGuesses() {
        game.reset();
        game.recordHigher();

        assertEquals(1, game.getNumGuesses());
    }


    // edge cases

    @Test
    void testFindingTarget_AtMinBound() {
        game.reset(); // 501
        int guess = 501;

        while(guess > 1) {
            guess = game.recordLower();
        }

        assertEquals(1, guess);
    }

    @Test
    void testFindingTarget_AtMaxBound() {
        game.reset(); // 501
        int guess = 501;

        while(guess < 1000) {
            guess = game.recordHigher();
        }

        assertEquals(1000, guess);
    }



    // getlastguess tests

    @Test
    void testGetLastGuess_AfterReset() {
        int guess = game.reset();

        assertEquals(guess, game.getLastGuess());
    }

    @Test
    void testGetLastGuess_AfterRecordLower() {
        game.reset();
        int newGuess = game.recordLower();

        assertEquals(newGuess, game.getLastGuess());
    }

    @Test
    void testGetLastGuess_AfterRecordHigher() {
        game.reset();
        int newGuess = game.recordHigher();

        assertEquals(newGuess, game.getLastGuess());
    }

    // get numb guesses

    @Test
    void testGetNumGuesses_IncreasesWithEachGuess() {
        game.reset();
        assertEquals(0, game.getNumGuesses());

        game.recordLower();
        assertEquals(1, game.getNumGuesses());

        game.recordHigher();
        assertEquals(2, game.getNumGuesses());

        game.recordLower();
        assertEquals(3, game.getNumGuesses());
    }


    @Test
    void testUpperBound_Value() {
        assertEquals(1000, ComputerGuessesGame.UPPER_BOUND);
    }

    @Test
    void testLowerBound_Value() {
        assertEquals(1, ComputerGuessesGame.LOWER_BOUND);
    }


    @Test
    void testReset_AfterGameInProgress() {
        game.reset();
        game.recordHigher();
        game.recordHigher();
        game.recordLower();

        assertEquals(3, game.getNumGuesses());

        int guess = game.reset();

        assertEquals(501, guess);
        assertEquals(0, game.getNumGuesses());
    }
}

