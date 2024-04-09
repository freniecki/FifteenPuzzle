package pl.firaanki;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableTest {

    @Test
    void moveTileTest() {

        int[][] test = {
                { 1, 2, 3, 4},
                { 5, 6, 7, 8},
                { 9,10, 0,12},
                {13,14,11,15}
        };

        Table table = new Table(4,4, test);

        String primary = table.toString();
        String expected = "[1, 2, 3, 4][5, 6, 7, 8][9, 10, 11, 12][13, 14, 0, 15]";

        System.out.println(table.moveTile('D'));
        System.out.println(expected);

        System.out.println(table.moveTile('U'));
        System.out.println(primary);

        String expectedHorizontal = "[1, 2, 3, 4][5, 6, 7, 8][9, 0, 10, 12][13, 14, 11, 15]";

        System.out.println(table.moveTile('L'));
        System.out.println(expectedHorizontal);

        System.out.println();

        System.out.println(table.moveTile('R'));
        System.out.println(primary);
    }

}