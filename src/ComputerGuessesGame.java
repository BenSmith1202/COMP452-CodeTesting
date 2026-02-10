/**
 * A game where the computer guesses a number between 1 and UPPER_BOUND
 * Tracks the bounds, the current guess, and the number of guesses made
 *
 * Separated from UI to enable unit testing
 */
public class ComputerGuessesGame {
    public final static int UPPER_BOUND = 1000;
    public final static int LOWER_BOUND = 1;

    private int numGuesses;
    private int lastGuess;

    // upperBound and lowerBound track the computer's knowledge about the correct number
    // They are updated after each guess is made
    private int upperBound; // correct number is <= upperBound
    private int lowerBound; // correct number is >= lowerBound

    public ComputerGuessesGame() {
        reset();
    }

    /**
     * Resets the game to initial state and returns the first guess
     */
    public int reset() {
        numGuesses = 0;
        upperBound = UPPER_BOUND;
        lowerBound = LOWER_BOUND;

        lastGuess = (lowerBound + upperBound + 1) / 2;
        return lastGuess;
    }

    /**
     * Records that the correct number is lower than the last guess
     * @return the new guess
     */
    public int recordLower() {
        upperBound = Math.min(upperBound, lastGuess);

        lastGuess = (lowerBound + upperBound + 1) / 2;
        numGuesses += 1;
        return lastGuess;
    }

    /**
     * Records that the correct number is higher than the last guess
     * @return the new guess
     */
    public int recordHigher() {
        lowerBound = Math.max(lowerBound, lastGuess + 1);

        lastGuess = (lowerBound + upperBound + 1) / 2;
        numGuesses += 1;
        return lastGuess;
    }

    public int getLastGuess() {
        return lastGuess;
    }

    public int getNumGuesses() {
        return numGuesses;
    }
}

