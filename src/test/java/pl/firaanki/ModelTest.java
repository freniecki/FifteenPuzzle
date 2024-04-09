package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void modelTest() {
        char[] order = {'L', 'R', 'U', 'D'};
        Model model = new Model(order);

        int[][] test = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 0, 12},
                {13, 14, 11, 15}
        };
        Table table = new Table(4, 4, test);

        Assertions.assertTrue(model.bfs(table));
    }
}