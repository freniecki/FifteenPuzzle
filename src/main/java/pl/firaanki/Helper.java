package pl.firaanki;

public class Helper {

    public static boolean verify(Table chart) {
        for (int i = 0; i < chart.getX(); i++) {
            for (int j = 0; j < chart.getY(); j++) {
                if (Table.PATTERN_CHART[i][j] != chart.getValue(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }



}
