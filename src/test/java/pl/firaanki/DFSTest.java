package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest {

    @Test
    void dfs() {
        char[] order = {'L', 'R', 'U', 'D'};
        DFS model = new DFS(order, 5);

        int[][] test = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 0, 12},
                {13, 14, 11, 15}
        };

        Table table = new Table(test, "");

        Assertions.assertTrue(model.dfs(table));
    }

    @Test
    void dfsFile() {
        char[] order = {'L', 'R', 'U', 'D'};
        DFS model = new DFS(order, 7);

        Table table = FileDaoFactory.getFile(
                "/home/firaanki/IdeaProjects/SISE/src/main/java/pl/firaanki/4x4_05_00006.txt")
                .read();

        Assertions.assertTrue(model.dfs(table));
    }
}