/**
 * Formats game result messages for display
 * Separated from UI to enable unit testing
 */
public class GameResultFormatter {

    /**
     * Formats the message showing what the correct answer was
     * @param result the game result
     * @return formatted answer message
     */
    public String formatAnswerMessage(GameResult result) {
        return "The answer was " + result.correctValue + ".";
    }

    /**
     * Formats the message showing how many guesses were taken
     * @param result the game result
     * @return formatted guesses message
     */
    public String formatGuessesMessage(GameResult result) {
        if(result.numGuesses == 1){
            return (result.humanWasPlaying ? "You" : "I") + " guessed it on the first try!";
        }
        else {
            return "It took " + (result.humanWasPlaying ? "you" : "me") + " " + result.numGuesses + " guesses.";
        }
    }
}

