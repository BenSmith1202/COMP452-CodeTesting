import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Parses CSV record fields for game statistics
 * Separated from file I/O to enable unit testing
 */
public class StatsRecordParser {

    /**
     * Parse a timestamp string into a LocalDateTime
     * @param timestampStr the string to parse
     * @return the parsed LocalDateTime
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public LocalDateTime parseTimestamp(String timestampStr) throws DateTimeParseException {
        return LocalDateTime.parse(timestampStr);
    }

    /**
     * Parse a string into the number of guesses
     * @param numGuessesStr the string to parse
     * @return the parsed number of guesses
     * @throws NumberFormatException if the string cannot be parsed as an integer
     */
    public int parseNumGuesses(String numGuessesStr) throws NumberFormatException {
        return Integer.parseInt(numGuessesStr);
    }

    /**
     * Check if a timestamp is within the specified number of days from now
     * @param timestamp the timestamp to check
     * @param days the number of days
     * @return true if the timestamp is within the specified days
     */
    public boolean isWithinDays(LocalDateTime timestamp, int days) {
        LocalDateTime limit = LocalDateTime.now().minusDays(days);
        return timestamp.isAfter(limit);
    }
}

