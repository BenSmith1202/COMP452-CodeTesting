import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for StatsCalculator class
 * Uses dependency injection with a test double to avoid file I/O
 */
public class StatsCalculatorTest {


    //Mockup for Dependency Injection
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




    //using dependency injection

    @Test
    void testCalculateBinCounts_BasicCase() {

        int[] gameData = {0, 5, 10, 8, 6, 4, 3, 2, 1, 1, 0};
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 4, 7, 10};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(4, binCounts.length);
    }

    @Test
    void testCalculateBinCounts_EmptyStats() {
        int[] gameData = new int[11]; // All 0s
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
        int[] gameData = {0, 2, 3, 4, 5, 6};
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1}; // single bin
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);

        assertEquals(1, binCounts.length);




    }



    //testing to see if it crashes with weird bins

    @Test
    void testCalculateBinCounts_AllGamesInFirstBin() {
        int[] gameData = {0, 10, 5, 0, 0, 0};
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 3, 5};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);


    }

    @Test
    void testCalculateBinCounts_AllGamesInLastBin() {
        int[] gameData = {0, 0, 0, 0, 0, 5, 10, 8};
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {1, 3, 5};
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);


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



    //EDGE CASES

    @Test
    void testCalculateBinCounts_SingleElementBinEdges() {
        int[] gameData = {0, 1, 2, 3, 4, 5};
        TestGameStats stats = new TestGameStats(gameData);

        int[] binEdges = {3}; // Only one bin starting at 3
        StatsCalculator calculator = new StatsCalculator();

        int[] binCounts = calculator.calculateBinCounts(stats, binEdges);



        assertEquals(1, binCounts.length);



    }

    @Test
    void testCalculateBinCounts_MaxGuessesEqualsZero() {
        int[] gameData = {}; //EMPTY
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

