package pl.firaanki;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static pl.firaanki.BFSTest.printMethod;

class TableTest {

    int[][] test = {
            { 1, 2, 3, 4},
            { 5, 6, 7, 8},
            { 9,10, 0,12},
            {13,14,11,15}
    };
    Table table = new Table(test);

    @Test
    void getX() {
        Assertions.assertEquals(4, table.getX());
    }

    @Test
    void getY() {
        Assertions.assertEquals(4, table.getY());
    }

    @Test
    void getValue() {
        Assertions.assertEquals(1, table.getValue(0,0));
    }

    @Test
    void getChart() {
        int[][] get = table.getChart();
        Assertions.assertArrayEquals(test, get);
    }

    @Test
    void getXZeroPosition() {
        Assertions.assertEquals(2, table.getXZeroPosition());
    }

    @Test
    void getYZeroPosition() {
        Assertions.assertEquals(2, table.getYZeroPosition());
    }

    @Test
    void moveTile() {
        String expD = "[1, 2, 3, 4][5, 6, 7, 8][9, 10, 11, 12][13, 14, 0, 15]";
        Assertions.assertEquals(expD, table.moveTile('D').toString());

        String expU = "[1, 2, 3, 4][5, 6, 7, 8][9, 10, 0, 12][13, 14, 11, 15]";
        Assertions.assertEquals(expU, table.moveTile('U').toString());

        String expR = "[1, 2, 3, 4][5, 6, 7, 8][9, 10, 12, 0][13, 14, 11, 15]";
        Assertions.assertEquals(expR, table.moveTile('R').toString());

        String expL = "[1, 2, 3, 4][5, 6, 7, 8][9, 10, 0, 12][13, 14, 11, 15]";
        Assertions.assertEquals(expL, table.moveTile('L').toString());

        /*----------------------------------------------*/

        int[][] test2 = {
                { 1, 2, 3, 4},
                { 5, 6, 7, 8},
                { 9,10, 11,12},
                {13,14,15,0}
        };
        Table table1 = new Table(test2);

        Assertions.assertNull(table1.moveTile('R'));
        Assertions.assertNull(table1.moveTile('D'));
    }

    @Test
    void testToString() {
        String primary = table.toString();
        String expected = "[1, 2, 3, 4][5, 6, 7, 8][9, 10, 0, 12][13, 14, 11, 15]";

        Assertions.assertEquals(expected, primary);
    }


}