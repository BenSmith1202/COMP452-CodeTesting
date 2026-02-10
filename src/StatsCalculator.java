/**
 * Contains logic for calculating statistics about game results
 * Separated from UI to enable unit testing
 */
public class StatsCalculator {

    /**
     * Calculate the number of games in each bin defined by binEdges
     * @param stats The game statistics data source
     * @param binEdges Array defining bin boundaries. Bin i goes from binEdges[i] to binEdges[i+1]-1 (inclusive)
     *                 The last bin includes binEdges[last] and all values above
     * @return Array of counts, one per bin
     */
    public int[] calculateBinCounts(GameStats stats, int[] binEdges) {
        int[] binCounts = new int[binEdges.length];

        for(int binIndex=0; binIndex<binEdges.length; binIndex++){
            final int lowerBound = binEdges[binIndex];
            int numGames = 0;

            if(binIndex == binEdges.length-1){
                // last bin
                // Sum all the results from lowerBound on up
                for(int numGuesses=lowerBound; numGuesses<stats.maxNumGuesses(); numGuesses++){
                    numGames += stats.numGames(numGuesses);
                }
            }
            else{
                int upperBound = binEdges[binIndex+1];
                for(int numGuesses=lowerBound; numGuesses <= upperBound; numGuesses++) {
                    numGames += stats.numGames(numGuesses);
                }
            }

            binCounts[binIndex] = numGames;
        }

        return binCounts;
    }
}

