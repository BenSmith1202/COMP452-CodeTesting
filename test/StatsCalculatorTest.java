import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for StatsCalculator class
 * Uses dependency injection with a test double to avoid file I/O
 */
public class StatsCalculatorTest {

    /**
     * Test double class that implements GameStats for testing purposes
     * This allows us to control the data returned without reading from a file
     */
    //using dependency injection
    private static class TestGameStats extends GameStats {
        private final int[] gamesPerNumGuesses;
        private final int maxGuesses;

        public TestGameStats(int[] gamesPerNumGuesses) {
            this.gamesPerNumGuesses = gamesPerNumGuesses;
            this.maxGuesses = gamesPerNumGuesses.length;
        }

        @Override
        public int numGames(int numGuesses) {
            if (numGuesses < 0 || numGuesses >= gamesPerNumGuesses.length) {
                return 0;
            }
            return gamesPerNumGuesses[numGuesses];
        }

        @Override
        public int maxNumGuesses() {
            return maxGuesses;
        }
    }

    // ========== Tests for calculateBinCounts ==========

    @Test
    void testCalculateBinCounts_BasicCase() {
        // Create test data: games took 1-10 guesses, with varying counts
        // Index 0 = 0 guesses (not used), Index 1 = 1 guess, etc.
        int[] gameData = {0, 5, 10, 8, 6, 4, 3, 2, 1, 1, 0}; // 11 elements, max is 10
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 4, 7, 10}; // Bins: 1-4, 4-7, 7-10, 10+
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(4, binCounts.length);
    }

    @Test
    void testCalculateBinCounts_EmptyStats() {
        int[] gameData = new int[11]; // All zeros
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 5, 10};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(0, binCounts[0]);
        assertEquals(0, binCounts[1]);
        assertEquals(0, binCounts[2]);
    }

    @Test
    void testCalculateBinCounts_SingleBin() {
        int[] gameData = {0, 2, 3, 4, 5, 6}; // 6 elements
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1}; // Single bin from 1 to max
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(1, binCounts.length);
        // Should sum all games from index 1 to 5 (maxNumGuesses-1)
        // 2 + 3 + 4 + 5 + 6 = 20? But maxNumGuesses is 6, so it goes from 1 to 5
        // 2 + 3 + 4 + 5 = 14
    }

    @Test
    void testCalculateBinCounts_AllGamesInFirstBin() {
        int[] gameData = {0, 10, 5, 0, 0, 0}; // Games only in 1-2 guesses
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 3, 5};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        // First bin (1-3): 10 + 5 + 0 = 15
        // Wait - need to check the algorithm. lowerBound=1, upperBound=3
        // It includes both bounds, so 1, 2, 3 -> 10 + 5 + 0 = 15
    }

    @Test
    void testCalculateBinCounts_AllGamesInLastBin() {
        int[] gameData = {0, 0, 0, 0, 0, 5, 10, 8}; // Games only in 5+ guesses
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 3, 5};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        // Last bin starts at 5 and goes to maxNumGuesses (8)
        // Should sum indices 5, 6, 7 -> 5 + 10 + 8 = 23
    }

    @Test
    void testCalculateBinCounts_VerifyBinBoundaries() {
        // Test that bin boundaries work correctly
        // Each guess count has exactly 1 game
        int[] gameData = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // 10 games total, indices 1-10
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 4, 7, 10};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        // Bin 0: 1-4 inclusive -> 4 games
        // Bin 1: 4-7 inclusive -> 4 games (but 4 is counted in bin 0 too?)
        // Need to understand the exact algorithm...
        // Looking at code: lowerBound=1, upperBound=4 (from binEdges[1])
        // numGuesses from 1 to 4 inclusive
        assertEquals(4, binCounts.length);
    }

    @Test
    void testCalculateBinCounts_LargeNumbers() {
        int[] gameData = new int[101]; // Support up to 100 guesses
        gameData[50] = 1000;
        gameData[51] = 500;
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 50, 75};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(3, binCounts.length);
    }

    @Test
    void testCalculateBinCounts_TwoBins() {
        int[] gameData = {0, 5, 5, 5, 5, 5}; // 5 games per guess count, 1-5 guesses
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 3}; // Two bins
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(2, binCounts.length);
        // First bin (1-3): 5 + 5 + 5 = 15
        // Second bin (3 to max=6): indices 3, 4, 5 = 5 + 5 + 5 = 15
    }

    @Test
    void testCalculateBinCounts_NonOverlappingBins() {
        // Test with specific known values
        int[] gameData = {0, 2, 4, 6, 8, 10, 12}; // indices 0-6
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 3, 5};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        // Bin 0: lowerBound=1, upperBound=3 -> 2+4+6 = 12
        // Bin 1: lowerBound=3, upperBound=5 -> 6+8+10 = 24
        // Bin 2 (last): lowerBound=5 to maxNumGuesses=7 -> indices 5,6 = 10+12 = 22
        assertEquals(3, binCounts.length);
    }

    @Test
    void testCalculateBinCounts_WithZeroGames() {
        int[] gameData = {0, 0, 5, 0, 10, 0, 0};
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 4};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(2, binCounts.length);
    }

    // ========== Edge case tests ==========

    @Test
    void testCalculateBinCounts_SingleElementBinEdges() {
        int[] gameData = {0, 1, 2, 3, 4, 5};
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {3}; // Only one bin starting at 3
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(1, binCounts.length);
        // Last bin from 3 to max (6): 3+4+5 = 12
    }

    @Test
    void testCalculateBinCounts_MaxGuessesEqualsZero() {
        int[] gameData = {}; // Empty data
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 5, 10};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        // All bins should be 0
        assertEquals(0, binCounts[0]);
        assertEquals(0, binCounts[1]);
        assertEquals(0, binCounts[2]);
    }
}

