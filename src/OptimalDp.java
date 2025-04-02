public class OptimalDp {
    int n;
    int[][] dp;
    int[] coins;
    int[][] choices;
    int[][] firstCoinPicked;
    //The constructor initializes n, dp, coins, and choices for the game.
    OptimalDp(int[] coins) {
        this.n = coins.length;
        this.dp = new int[n][n];
        this.coins = coins;
        this.choices = new int[n][n];
        this.firstCoinPicked = new int[n][n];

    }

    /*Calculates the optimal score a player can achieve by playing optimally.
     * @return the maximum score the player can secure.
     */
    public int getOptimalScore() {



        // This first loop sets dp[i][i] = coins[i]
        // because if there’s only one coin (i == j),
        // the only option is to take that coin’s value.
        for (int i = 0; i < n; i++) {
            dp[i][i] = coins[i];
        }

        // Handle the case when there are exactly two coins (i == j-1)
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = max(coins[i], coins[i + 1]);
            choices[i][i + 1] = (coins[i] > coins[i + 1]) ? i : i + 1;
            firstCoinPicked[i][i + 1] = (coins[i] > coins[i + 1])? 1 : 0;
        }

        //subarrays with more than two coins
        for (int k = 2; k < n; k++) {

            for (int i = 0, j = k; j < n; i++, j++) {

                int x = 0, y = 0, z = 0;
                // If the player picks coin i and the opponent also picks coin i
                if (i + 2 <= j) {
                    x = dp[i + 2][j];
                }
                // If the opponent picks the opposite of the player's choice
                if (i + 1 <= j - 1) {
                    y = dp[i + 1][j - 1];
                }
                // If the player picks coin j and the opponent also picks coin j
                if (i <= j - 2) {
                    z = dp[i][j - 2];
                }


                // Calculate the optimal values if the player chooses the first or last coin
                int first = coins[i] + min(x, y);
                int last = coins[j] + min(y, z);

                // choices[i][j] stores i or j to keep track of which coin
                // was selected for the optimal choice in each subarray
                if (first > last) {
                    dp[i][j] = first;
                    choices[i][j] = i;
                    firstCoinPicked[i][j] = 1;
                } else if (first < last) {
                    dp[i][j] = last;
                    choices[i][j] = j;
                    firstCoinPicked[i][j] = 0;
                }else{
                    dp[i][j] = first;
                    if(coins[i]>coins[j]) {
                        choices[i][j] = i;
                        firstCoinPicked[i][j] = 1;
                    }else{
                        choices[i][j] = j;
                        firstCoinPicked[i][j] = 0;
                    }
                    }
            }
        }

        return dp[0][n - 1];
    }


    /*
     *This method reconstructs the optimal sequence of coins taken by the player:
     *It initializes i and j as the start and end indices of the coins.
     *For each step, it appends the coin value at choices[i][j] to the result,
     *then updates i or j based on the coin chosen.
     */
    public String getOptimalCoins() {
        StringBuilder result = new StringBuilder();
        int i = 0, j = n - 1;

        // Iterate while the subarray has elements
        while (i <= j) {

            // Add the chosen coin's value to the result and update indices
            if (choices[i][j] == i) {
                result.append(coins[i]).append(",");
                i++;
            } else {
                result.append(coins[j]).append(",");
                j--;
            }
        }
        return result.toString();
    }

    // Helper method to find the minimum of two integers
    private static int min(int a, int b) {
        if (a < b)
            return a;

        return b;
    }
    private static int max(int a, int b) {
        if (a > b)
            return a;

        return b;
    }



}
