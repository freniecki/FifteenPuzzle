package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DFSTest {

    @Test
    void dfs() {
        char[] order = {'L', 'R', 'U', 'D'};
        DFS model = new DFS(order, 20);

        int[][] test = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 0},
                {13, 14, 15, 12}
        };

        Table table = new Table(test, "");

        Assertions.assertTrue(model.solve(table));
        System.out.println(model.getResults());
    }

    @Test
    void dfsFile() {
        char[] order = {'L', 'R', 'U', 'D'};
        DFS model = new DFS(order, 20);

        Table table = FileDaoFactory.getFile(
                "/home/firaanki/IS_4sem/SISE/puzzles/4x4_05_00006.txt")
                .read();

        Assertions.assertTrue(model.solve(table));
        System.out.println(model.getResults());

    }
}