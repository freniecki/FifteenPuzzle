package pl.firaanki;

import java.util.Arrays;

public class Table {
    private final int[][] chart;
    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;

    String steps = "";

    Table(int[][] chart) {
        this.chart = chart;
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

        Table newTab = new Table(this.getChart());

        switch (direction) {
            case 'L':
                if (yZero != 0) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero][yZero - 1];
                    newTab.chart[xZero][yZero - 1] = tmp;
                    steps += 'L';
                } else {
                    return null;
                }
                break;

            case 'R':
                if (yZero < Y_SIZE - 1) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero][yZero + 1];
                    newTab.chart[xZero][yZero + 1] = tmp;
                    steps += 'R';
                } else {
                    return null;
                }
                break;

            case 'U':
                if (xZero != 0) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero - 1][yZero];
                    newTab.chart[xZero - 1][yZero] = tmp;
                    steps += 'U';
                } else {
                    return null;
                }
                break;

            case 'D':
                if (xZero < X_SIZE - 1) {
                    int tmp = newTab.chart[xZero][yZero];
                    newTab.chart[xZero][yZero] = newTab.chart[xZero + 1][yZero];
                    newTab.chart[xZero + 1][yZero] = tmp;
                    steps += 'D';
                } else {
                    return null;
                }
                break;
            default:
                return null;
        }
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
        for (int i = 0; i < X_SIZE; i++) {
            stringBuilder.append(Arrays.toString(chart[i]));
        }
        return stringBuilder.toString();
    }
}
