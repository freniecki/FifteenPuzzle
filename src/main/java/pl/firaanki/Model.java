package pl.firaanki;

import java.util.Queue;

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
    private String solution = "";

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
        }
        return true;
    }

    public boolean verifySides(Table chartToMove) {
        boolean check = false;
        for (int i = 0; i < 4; i++) {
            check = verify(chartToMove.moveTile(order[i]));
            if (check) {
                solution += order[i];
            }
        }
        return check;
    }

    boolean bfs(Table chartToSolve) {

        Queue<Table> queue = new Queue<Table>();


        if (verify(chartToSolve)) {
            return true;
        }

        verifySides(chartToSolve);
    }

}