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


    private final char[] order;

    /* --------------------------------------------------------------------- */
    Model(int x, int y, char[] order)  {
        X_SIZE = x;
        Y_SIZE = y;
        this.order = order;
    }

    public int getX_SIZE() {
        return X_SIZE;
    }

    public int getY_SIZE() {
        return Y_SIZE;
    }

    public boolean verify(Table chart) {
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                if (PATTERN_CHART[i][j] != chart.getValue(i, j)) {
                    return false;
                }
            }
        };
        return true;
    }


    boolean bfs(Table chartToSolve) {

        if (verify(chartToSolve)) {
            return true;
        } else {
            for (int i = 0; i < 4; i++) {

            }
        }
    }

}