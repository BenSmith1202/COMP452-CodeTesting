import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GameResultFormatter class
 */
public class GameResultFormatterTest {

    private GameResultFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new GameResultFormatter();
    }

    // format message answer

    @Test
    void testFormatAnswerMessage_BasicValue() {
        GameResult result = new GameResult(true, 500, 5);

        String message = formatter.formatAnswerMessage(result);

        assertEquals("The answer was 500.", message);
    }




    @Test
    void testFormatAnswerMessage_MinValue() {
        GameResult result = new GameResult(true, 1, 10);

        String message = formatter.formatAnswerMessage(result);

        assertEquals("The answer was 1.", message);
    }

    @Test
    void testFormatAnswerMessage_MaxValue() {
        GameResult result = new GameResult(true, 1000, 1);

        String message = formatter.formatAnswerMessage(result);

        assertEquals("The answer was 1000.", message);
    }

    @Test
    void testFormatAnswerMessage_HumanWasNotPlaying() {
        GameResult result = new GameResult(false, 750, 8);

        String message = formatter.formatAnswerMessage(result);

        assertEquals("The answer was 750.", message);
    }

    // format guesses message

    @Test
    void testFormatGuessesMessage_HumanPlaying_OneGuess() {
        GameResult result = new GameResult(true, 500, 1);

        String message = formatter.formatGuessesMessage(result);

        assertEquals("You guessed it on the first try!", message);
    }

    @Test
    void testFormatGuessesMessage_HumanPlaying_MultipleGuesses() {
        GameResult result = new GameResult(true, 500, 5);

        String message = formatter.formatGuessesMessage(result);

        assertEquals("It took you 5 guesses.", message);
    }

    @Test
    void testFormatGuessesMessage_HumanPlaying_ManyGuesses() {
        GameResult result = new GameResult(true, 500, 15);

        String message = formatter.formatGuessesMessage(result);

        assertEquals("It took you 15 guesses.", message);
    }


    @Test
    void testFormatGuessesMessage_ComputerPlaying_OneGuess() {
        GameResult result = new GameResult(false, 500, 1);

        String message = formatter.formatGuessesMessage(result);

        assertEquals("I guessed it on the first try!", message);
    }




    @Test
    void testFormatGuessesMessage_ComputerPlaying_MultipleGuesses() {
        GameResult result = new GameResult(false, 500, 7);

        String message = formatter.formatGuessesMessage(result);

        assertEquals("It took me 7 guesses.", message);
    }





    @Test
    void testFormatGuessesMessage_ComputerPlaying_Many() {
        GameResult result = new GameResult(false, 500, 20);

        String message = formatter.formatGuessesMessage(result);

        assertEquals("It took me 20 guesses.", message);
    }


    // EDGE CASES

    @Test
    void testFormatGuessesMessage_ZeroGuesses() {
        // trying 0
        GameResult result = new GameResult(true, 500, 0);

        String message = formatter.formatGuessesMessage(result);


        assertEquals("It took you 0 guesses.", message);
    }

    @Test
    void testFormatGuessesMessage_NegativeGuesses() {
        // impossible num guesses
        GameResult result = new GameResult(true, 500, -1);

        String message = formatter.formatGuessesMessage(result);

        // Should go to else branch since -1 != 1
        assertEquals("It took you -1 guesses.", message);
    }

    @Test
    void testFormatAnswerMessage_ZeroValue() {
        // trying 0
        GameResult result = new GameResult(true, 0, 5);

        String message = formatter.formatAnswerMessage(result);

        assertEquals("The answer was 0.", message);
    }




    @Test
    void testFormatAnswerMessage_NegativeValue() {
        // try using negative answer
        GameResult result = new GameResult(true, -100, 5);

        String message = formatter.formatAnswerMessage(result);

        assertEquals("The answer was -100.", message);
    }
}

