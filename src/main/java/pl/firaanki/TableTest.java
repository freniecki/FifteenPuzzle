package pl.firaanki;


class TableTest {

    public static void printMethod(int x, int y, int[][] chart) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(chart[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Table table = new Table(4,4);

        int[][] test = {
                { 1, 2, 3, 4},
                { 5, 6, 7, 8},
                { 9,10, 0,12},
                {13,14,11,15}
        };

        printMethod(4,4, table.moveTile('L'));

    }

}