package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static pl.firaanki.BFSTest.printMethod;

class FileDaoTest {
    @Test
    void read() {
        Table test = FileDaoFactory
                .getFile("/home/firaanki/IdeaProjects/SISE/src/test/java/pl/firaanki/4x4_06_00006.txt")
                .read();

        printMethod(test);

        int[][] chart = {
                {0, 1, 2, 4},
                {5, 6, 3, 7},
                {9, 10, 11, 8},
                {13, 14, 15, 12}
        };

        Assertions.assertArrayEquals(chart, test.getChart());
        char[] order = {'L', 'R', 'U', 'D'};
        BFS bfs = new BFS(order);

        Assertions.assertTrue(bfs.solve(test));
    }

    @Test
    void write() {
        List<String> message = new ArrayList<>();
        Table test = FileDaoFactory
                .getFile("/home/firaanki/IdeaProjects/SISE/src/test/java/pl/firaanki/4x4_06_00006.txt")
                .read();

        message.add("4 4\n");
        int[][] chart = test.getChart();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                line.append(chart[i][j]);
                line.append(" ");
            }
            line.append("\n");
            message.add(String.valueOf(line));
            line = new StringBuilder();
        }

        FileDaoFactory
                .getFile("/home/firaanki/IdeaProjects/SISE/src/test/java/pl/firaanki/saveToFile.txt")
                .write(message);
    }
}