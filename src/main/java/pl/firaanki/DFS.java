package pl.firaanki;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DFS {

    private final Map<Table, List<Table>> adjacencyList = new HashMap<>();

    private final char[] order = new char[4];
    Logger logger = Logger.getLogger(getClass().getName());

    DFS(char[] order) {
        System.arraycopy(order, 0, this.order, 0, 4);
    }

    public Map<Table, List<Table>> getAdjacencyList() {
        return adjacencyList;
    }

    public char[] getOrder() {
        return order;
    }


}
