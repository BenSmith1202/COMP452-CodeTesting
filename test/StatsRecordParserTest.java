import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;




/**
 * Unit tests for StatsRecordParser class
 * Tests parsing logic and formatting exceptions
 */
public class StatsRecordParserTest {

    private StatsRecordParser parser;

    @BeforeEach
    void setUp() {
        parser = new StatsRecordParser();
    }




    @Test
    void testParseTimestamp_ValidFormat() {
        String timestampStr = "2026-02-10T14:30:00";

        LocalDateTime result = parser.parseTimestamp(timestampStr);

        assertEquals(2026, result.getYear());
        assertEquals(2, result.getMonthValue());
        assertEquals(10, result.getDayOfMonth());
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
    }

    @Test
    void testParseTimestamp_Midnight() {
        String timestampStr = "2026-01-01T00:00:00";

        LocalDateTime result = parser.parseTimestamp(timestampStr);

        assertEquals(0, result.getHour());
        assertEquals(0, result.getMinute());
    }



//invalid tests


    @Test
    void testParseTimestamp_InvalidFormat_ThrowsException() {
        String timestampStr = "invalid-date";

        assertThrows(DateTimeParseException.class, () -> {
            parser.parseTimestamp(timestampStr);
        });
    }

    @Test
    void testParseTimestamp_EmptyString_ThrowsException() {
        String timestampStr = "";

        assertThrows(DateTimeParseException.class, () -> {
            parser.parseTimestamp(timestampStr);
        });
    }





    @Test
    void testParseTimestamp_Null_ThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            parser.parseTimestamp(null);
        });
    }

//valid tests


    @Test
    void testParseNumGuesses_ValidNumber() {
        String numGuessesStr = "10";

        int result = parser.parseNumGuesses(numGuessesStr);

        assertEquals(10, result);
    }

    @Test
    void testParseNumGuesses_SingleDigit() {
        String numGuessesStr = "5";

        int result = parser.parseNumGuesses(numGuessesStr);

        assertEquals(5, result);
    }

    @Test
    void testParseNumGuesses_LargeNumber() {
        String numGuessesStr = "1000";

        int result = parser.parseNumGuesses(numGuessesStr);

        assertEquals(1000, result);
    }

    @Test
    void testParseNumGuesses_Zero() {
        String numGuessesStr = "0";

        int result = parser.parseNumGuesses(numGuessesStr);

        assertEquals(0, result);
    }













    @Test
    void testParseNumGuesses_EmptyString_ThrowsException() {
        String numGuessesStr = "";

        assertThrows(NumberFormatException.class, () -> {
            parser.parseNumGuesses(numGuessesStr);
        });
    }

    @Test
    void testParseNumGuesses_Decimal_ThrowsException() {
        String numGuessesStr = "5.5";

        assertThrows(NumberFormatException.class, () -> {
            parser.parseNumGuesses(numGuessesStr);
        });
    }


    @Test
    void testParseNumGuesses_MixedContent_ThrowsException() {
        String numGuessesStr = "10abc";

        assertThrows(NumberFormatException.class, () -> {
            parser.parseNumGuesses(numGuessesStr);
        });
    }




    @Test
    void testParseNumGuesses_Null_ThrowsException() {
        assertThrows(NumberFormatException.class, () -> {
            parser.parseNumGuesses(null);
        });
    }




    @Test
    void testIsWithinDays_Recent_ReturnsTrue() {
        LocalDateTime timestamp = LocalDateTime.now().minusDays(1);

        assertTrue(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_Old_ReturnsFalse() {
        LocalDateTime timestamp = LocalDateTime.now().minusDays(60);

        assertFalse(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_Exactly30_ReturnsFalse() {

        LocalDateTime timestamp = LocalDateTime.now().minusDays(30);

        assertFalse(parser.isWithinDays(timestamp, 30));
    }




    @Test
    void testIsWithinDays_Future_ReturnsTrue() {
        LocalDateTime timestamp = LocalDateTime.now().plusDays(5);

        assertTrue(parser.isWithinDays(timestamp, 30));
    }

 
}

