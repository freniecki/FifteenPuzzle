package pl.firaanki;

import javax.print.DocFlavor;

public class Table {
    private int[][] chart;
    private int xSize;
    private int ySize;

    Table(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        chart = new int[xSize][ySize];

    }

    public int getValue(int x, int y) {
        return chart[x][y];
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

    public int[][] moveTile(char direction) {

        switch (direction) {
            case 'L':
                if (ySize == 0) {
                    // error
                } else {
                    int tmp = chart[xSize][ySize];
                    chart[xSize][ySize] = chart[xSize][ySize - 1];
                    chart[xSize][ySize - 1] = tmp;
                }
                break;

            case 'R':
                if (ySize == ySize - 1) {
                    // error
                } else {
                    int tmp = chart[xSize][ySize];
                    chart[xSize][ySize] = chart[xSize][ySize + 1];
                    chart[xSize][ySize + 1] = tmp;
                }
                break;

            case 'U':
                if (xSize == 0) {
                    //error
                } else {
                    int tmp = chart[xSize][ySize];
                    chart[xSize][ySize] = chart[xSize + 1][ySize];
                    chart[xSize + 1][ySize] = tmp;
                }
                break;

            case 'D':
                if (xSize == xSize - 1) {
                    // error
                } else {
                    int tmp = chart[xSize][ySize];
                    chart[xSize][ySize] = chart[xSize - 1][ySize];
                    chart[xSize - 1][ySize] = tmp;
                }

                break;
        }
        return chart;
    }
}
