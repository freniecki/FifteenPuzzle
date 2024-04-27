package pl.firaanki;

import java.util.Comparator;

public class CustomComparator implements Comparator<Table> {

    @Override
    public int compare(Table table, Table compare) {
        int value = table.getDistance().compareTo(compare.getDistance());
        return Integer.compare(value, 0);
    }
}
