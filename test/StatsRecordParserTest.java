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

    // ========== Tests for parseTimestamp - valid cases ==========

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

    @Test
    void testParseTimestamp_EndOfDay() {
        String timestampStr = "2026-12-31T23:59:59";

        LocalDateTime result = parser.parseTimestamp(timestampStr);

        assertEquals(23, result.getHour());
        assertEquals(59, result.getMinute());
        assertEquals(59, result.getSecond());
    }

    @Test
    void testParseTimestamp_WithoutSeconds() {
        String timestampStr = "2026-02-10T14:30";

        LocalDateTime result = parser.parseTimestamp(timestampStr);

        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
        assertEquals(0, result.getSecond());
    }

    // ========== Tests for parseTimestamp - invalid cases (expect exceptions) ==========

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
    void testParseTimestamp_WrongDateFormat_ThrowsException() {
        // US date format instead of ISO
        String timestampStr = "02/10/2026 14:30:00";

        assertThrows(DateTimeParseException.class, () -> {
            parser.parseTimestamp(timestampStr);
        });
    }

    @Test
    void testParseTimestamp_InvalidMonth_ThrowsException() {
        String timestampStr = "2026-13-10T14:30:00";

        assertThrows(DateTimeParseException.class, () -> {
            parser.parseTimestamp(timestampStr);
        });
    }

    @Test
    void testParseTimestamp_InvalidDay_ThrowsException() {
        String timestampStr = "2026-02-30T14:30:00";

        assertThrows(DateTimeParseException.class, () -> {
            parser.parseTimestamp(timestampStr);
        });
    }

    @Test
    void testParseTimestamp_MissingTime_ThrowsException() {
        String timestampStr = "2026-02-10";

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

    // ========== Tests for parseNumGuesses - valid cases ==========

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
    void testParseNumGuesses_NegativeNumber() {
        // Should parse negative numbers (even if invalid in context)
        String numGuessesStr = "-5";

        int result = parser.parseNumGuesses(numGuessesStr);

        assertEquals(-5, result);
    }

    // ========== Tests for parseNumGuesses - invalid cases (expect exceptions) ==========

    @Test
    void testParseNumGuesses_NonNumeric_ThrowsException() {
        String numGuessesStr = "abc";

        assertThrows(NumberFormatException.class, () -> {
            parser.parseNumGuesses(numGuessesStr);
        });
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
    void testParseNumGuesses_WithSpaces_ThrowsException() {
        String numGuessesStr = " 10 ";

        // Integer.parseInt does not trim whitespace
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

    // ========== Tests for isWithinDays ==========

    @Test
    void testIsWithinDays_Recent_ReturnsTrue() {
        // A timestamp from 1 day ago should be within 30 days
        LocalDateTime timestamp = LocalDateTime.now().minusDays(1);

        assertTrue(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_Old_ReturnsFalse() {
        // A timestamp from 60 days ago should NOT be within 30 days
        LocalDateTime timestamp = LocalDateTime.now().minusDays(60);

        assertFalse(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_ExactlyAtLimit_ReturnsFalse() {
        // A timestamp from exactly 30 days ago should NOT be after the limit
        // (it's not AFTER, it's equal to or before)
        LocalDateTime timestamp = LocalDateTime.now().minusDays(30);

        assertFalse(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_JustInsideLimit_ReturnsTrue() {
        // A timestamp from just under 30 days ago
        LocalDateTime timestamp = LocalDateTime.now().minusDays(29).minusHours(23);

        assertTrue(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_Now_ReturnsTrue() {
        LocalDateTime timestamp = LocalDateTime.now();

        assertTrue(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_Future_ReturnsTrue() {
        // Future dates should be "within" the past 30 days (they're after the limit)
        LocalDateTime timestamp = LocalDateTime.now().plusDays(5);

        assertTrue(parser.isWithinDays(timestamp, 30));
    }

    @Test
    void testIsWithinDays_ZeroDays() {
        // With 0 days, only future timestamps should return true
        LocalDateTime recent = LocalDateTime.now().minusMinutes(1);
        LocalDateTime future = LocalDateTime.now().plusMinutes(1);

        assertFalse(parser.isWithinDays(recent, 0));
        assertTrue(parser.isWithinDays(future, 0));
    }
}

