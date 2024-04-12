package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pl.firaanki.BFSTest.printMethod;

class FileDaoTest {
    @Test
    void read() {
        Table test = FileDaoFactory.getFile(
                "/home/firaanki/IdeaProjects/SISE_v2/src/main/java/pl/firaanki/4x4_05_00006.txt").read();

        printMethod(test);

        int[][] chart = {
                {1, 2, 3, 4},
                {0, 5, 6, 7},
                {9, 10, 11, 8},
                {13, 14, 15, 12}
        };

        Assertions.assertArrayEquals(chart, test.getChart());
    }
}