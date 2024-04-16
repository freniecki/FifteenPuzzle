package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class AStarTest {

    @Test
    void getKeys() {
        AStar astar = new AStar("manhattan");
        Map<Double, Table> map = new HashMap<>();

        int[][] test = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 0, 12},
                {13, 14, 11, 15}
        };

        Table tabTest = new Table(test, "");
        Table tab = new Table(test, "");

        map.put(1.0, tabTest);
        map.put(2.0, tabTest);
        map.put(3.0, tab);
        map.put(4.0, tab);
        Set<Double> keys = astar.getKeys(map, tab);

        Assertions.assertEquals(2, keys.size());
    }

    @Test
    void getMinFromSet() {
        AStar astar = new AStar("manhattan");
        Map<Double, Table> map = new HashMap<>();

        int[][] test = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 0, 12},
                {13, 14, 11, 15}
        };

        Table tabTest = new Table(test, "");
        Table tab = new Table(test, "");

        map.put(1.0, tabTest);
        map.put(2.0, tabTest);
        map.put(3.0, tab);
        map.put(4.0, tab);
        Set<Double> keys = astar.getKeys(map, tab);

        Assertions.assertEquals(3.0, astar.getMinFromSet(keys));
    }

    @Test
    void solve() {

        int[][] test = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 0, 12},
                {13, 14, 11, 15}
        };

        Table table = new Table(test, "");
        AStar astar = new AStar("hamming");

        Assertions.assertTrue(astar.solve(table));
    }

    @Test
    void solveFile() {
        Table table = FileDaoFactory.getFile(
                        "/home/firaanki/IdeaProjects/SISE/src/main/java/pl/firaanki/4x4_05_00006.txt")
                .read();
        AStar astar = new AStar("hamming");

        Assertions.assertTrue(astar.solve(table));
    }
}