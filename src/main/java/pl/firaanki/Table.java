package pl.firaanki;

import java.util.Arrays;

public class Table {
    private final int[][] chart;
    private final int xSize;
    private final int ySize;

    Table(int xSize, int ySize, int[][] chart) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.chart = chart;
    }

    public int getX() {
        return xSize;
    }

    public int getY() {
        return ySize;
    }

    public int getValue(int x, int y) {
        return chart[x][y];
    }

    public int[][] getChart() {
        int[][] newChart = new int[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            System.arraycopy(chart[i], 0, newChart[i], 0, ySize);
        }
        return newChart;
    }

    int getXZeroPosition() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (chart[i][j] == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
    int getYZeroPosition() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
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

        switch (direction) {
            case 'L':
                if (yZero != 0) {
                    int tmp = chart[xZero][yZero];
                    chart[xZero][yZero] = chart[xZero][yZero - 1];
                    chart[xZero][yZero - 1] = tmp;
                }
                break;

            case 'R':
                if (yZero < ySize - 1) {
                    int tmp = chart[xZero][yZero];
                    chart[xZero][yZero] = chart[xZero][yZero + 1];
                    chart[xZero][yZero + 1] = tmp;
                }
                break;

            case 'U':
                if (xZero != 0) {
                    int tmp = chart[xZero][yZero];
                    chart[xZero][yZero] = chart[xZero - 1][yZero];
                    chart[xZero - 1][yZero] = tmp;
                }
                break;

            case 'D':
                if (xZero < xSize - 1) {
                    int tmp = chart[xZero][yZero];
                    chart[xZero][yZero] = chart[xZero + 1][yZero];
                    chart[xZero + 1][yZero] = tmp;
                }
                break;
        }
        return this;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < xSize; i++) {
            stringBuilder.append(Arrays.toString(chart[i]));
        }
        return stringBuilder.toString();
    }
}
