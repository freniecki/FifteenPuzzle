package pl.firaanki;

import org.junit.jupiter.api.Test;

class ModelTest {

    public void printMethod(int x, int y, int[][] chart) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(chart[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Test
    void modelTest() {
        int[][] base = {
                { 1, 2, 3, 4},
                { 5, 6, 7, 8},
                { 9,10, 0,12},
                {13,14,11,15}
        };
        char[] order = {'L','R','U', 'D'};
        Model model = new Model(4,4, base, order);


    }

}