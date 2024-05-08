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

    private Double distance = 0.0;

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

    int getXPosition() {
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                if (chart[i][j] == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    int getYPosition() {
        for (int i = 0; i < X_SIZE; i++) {
            for (int j = 0; j < Y_SIZE; j++) {
                if (chart[i][j] == 0) {
                    return j;
                }
            }
        }
        return -1;
    }

    Double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    boolean checkPreviousStep(char direction) {
        if (direction == 'L' && steps.endsWith("R")) {
            return false;
        } else if (direction == 'R' && steps.endsWith("L")) {
            return false;
        } else if (direction == 'U' && steps.endsWith("D")) {
            return false;
        } else return direction != 'D' || !steps.endsWith("U");
    }

    public Table moveTile(char direction) {

        if (!checkPreviousStep(direction)) {
            return null;
        }

        int xZero = getXPosition();
        int yZero = getYPosition();

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

    /*
    f - g + h
    g - starting point to given position
    h - given position to final destination

    metrics:
    Hamming - count of every number not in place
    Manhattan - sum of count of steps for each number to be in place
    */

    public Double getHammingEnd() {
        Double hamming = 0.0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (chart[i][j] != PATTERN_CHART[i][j]) {
                    hamming++;
                }
            }
        }

        return hamming;
    }

    public Double getManhattanEnd() {
        double manhattan = 0.0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (chart[i][j] != PATTERN_CHART[i][j]) {
                    if (chart[i][j] == 0) {
                        manhattan += (double) Math.abs(i - 3) + (double) Math.abs(j - 3);
                    } else {
                        int xBasePosition = (chart[i][j] - 1 ) / 4;
                        int yBasePosition = (chart[i][j] - 1 ) % 4;
                        manhattan += (double) Math.abs(i - xBasePosition) + (double) Math.abs(j - yBasePosition);
                    }
                }
            }
        }
        return manhattan;
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
