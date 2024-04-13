package pl.firaanki;

import java.util.Arrays;

public class Table {

    protected static final int[][] PATTERN_CHART = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    private final int[][] chart;
    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;

    private String steps = "";

    Table(int[][] chart, String steps) {
        this.chart = chart;
        this.steps += steps;
    }

    public int getX() {
        return X_SIZE;
    }

    public int getY() {
        return Y_SIZE;
    }

    public int getValue(int x, int y) {
        return chart[x][y];
    }

    public int[][] getChart() {
        int[][] newChart = new int[X_SIZE][Y_SIZE];
        for (int i = 0; i < X_SIZE; i++) {
            System.arraycopy(chart[i], 0, newChart[i], 0, Y_SIZE);
        }
        return newChart;
    }

    int getXZeroPosition() {
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                if (chart[i][j] == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
    int getYZeroPosition() {
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                if (chart[i][j] == 0) {
                    return j;
                }
            }
        }
        return -1;
    }

    public Table moveTile(char direction) {
        int xZero = getXZeroPosition();
        int yZero = getYZeroPosition();

        Table newTab = new Table(this.getChart(), steps);

        switch (direction) {
            case 'L':
                if (yZero != 0) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero][yZero - 1];
                    newTab.chart[xZero][yZero - 1] = tmp;
                } else {
                    return null;
                }
                break;

            case 'R':
                if (yZero < Y_SIZE - 1) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero][yZero + 1];
                    newTab.chart[xZero][yZero + 1] = tmp;
                } else {
                    return null;
                }
                break;

            case 'U':
                if (xZero != 0) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero - 1][yZero];
                    newTab.chart[xZero - 1][yZero] = tmp;
                } else {
                    return null;
                }
                break;

            case 'D':
                if (xZero < X_SIZE - 1) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero + 1][yZero];
                    newTab.chart[xZero + 1][yZero] = tmp;
                } else {
                    return null;
                }
                break;
            default:
                return null;
        }
        newTab.steps += direction;
        return newTab;
    }

    String getSteps() {
        return steps;
    }

    String getStepsCount() {
        return String.valueOf(steps.length());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\n');
        for (int i = 0; i < X_SIZE; i++) {
            stringBuilder.append(Arrays.toString(chart[i]));
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
