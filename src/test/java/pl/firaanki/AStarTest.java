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

        map.put(1.0, new Table(test, ""));
        map.put(2.0, new Table(test, ""));
        map.put(3.0, new Table(test, ""));
        map.put(4.0, new Table(test,""));
        Table tab = new Table(test, "");
        Set<Double> keys = astar.getKeys(map, tab);

        Assertions.assertEquals(3, keys.size());
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
        AStar astar = new AStar("manhattan");

        Assertions.assertTrue(astar.solve(table));
    }
}