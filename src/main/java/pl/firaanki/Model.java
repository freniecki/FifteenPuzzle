package pl.firaanki;

import static java.util.Arrays.compare;

public class Model {

    private final int X_SIZE;
    private final int Y_SIZE;

    private static final int[][] PATTERN_CHART = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    private int[][] chart;

    private final char[] order;

    /* --------------------------------------------------------------------- */
    Model(int x, int y, int[][] baseChart, char[] order)  {
        X_SIZE = x;
        Y_SIZE = y;
        this.chart = baseChart;
        this.order = order;
    }

    public int[][] getChart() {
        return chart;
    }

    public int getX_SIZE() {
        return X_SIZE;
    }

    public int getY_SIZE() {
        return Y_SIZE;
    }

    public boolean verify(int[][] chart) {
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                if (PATTERN_CHART[i][j] != this.chart[i][j]) {
                    return false;
                }
            }
        };
        return true;
    }

    /**
     * Main DFS method
     * @param direction tells the directions of change
     */
    public void DFS(char direction) {
        // wykonaj ruch
        // sprawdz czy poprawny

        swapNumbers(direction);

        if (!verify(chart)) {
            DFS();
        }
    }

    /**
     * Swap 0 with other number in given direction
     */
    private void swapNumbers(char direction) {
        if (direction == 'L') {

        } else if (direction == 'R') {

        } else if (direction == 'U') {

        } else  {

        }
    }


}