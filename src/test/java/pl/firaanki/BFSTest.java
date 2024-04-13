package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BFSTest {

    public static void printMethod(Table chart) {
        for (int i = 0; i < chart.getX(); i++) {
            for (int j = 0; j < chart.getY(); j++) {
                System.out.print(chart.getValue(i,j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Test
    void getOrder() {
        char[] order = {'L', 'R', 'U', 'D'};
        BFS BFS = new BFS(order);

        Assertions.assertArrayEquals(order, BFS.getOrder());
    }

    @Test
    void bfs() {
        char[] order = {'L', 'R', 'U', 'D'};
        BFS model = new BFS(order);

        int[][] test = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 0, 12},
                {13, 14, 11, 15}
        };

        Table table = new Table(test, "");

        Assertions.assertTrue(model.bfs(table));
    }
}