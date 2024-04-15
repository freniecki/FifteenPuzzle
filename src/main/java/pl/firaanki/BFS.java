package pl.firaanki;

import java.util.*;
import java.util.logging.Logger;

public class BFS {

    private final Map<Table, List<Table>> adjacencyList = new HashMap<>();

    private final char[] order = new char[4];

    Logger logger = Logger.getLogger(getClass().getName());
    /* --------------------------------------------------------------------- */
    BFS(char[] order) {
        System.arraycopy(order, 0, this.order, 0, 4);
    }

    public char[] getOrder() {
        return order;
    }

    void addEdge(Table parent, Table child) {
        adjacencyList.putIfAbsent(parent, new ArrayList<>());
        adjacencyList.putIfAbsent(child, new ArrayList<>());
        adjacencyList.get(parent).add(child);
    }

    void fillAdjacencyList(Table chartToSolve) {
        for (int i = 0; i < 4; i++) {
            Table neighbour = chartToSolve.moveTile(order[i]);

            if (neighbour != null) {
                addEdge(chartToSolve, neighbour);
            }
        }
    }

    public boolean bfs(Table chartToSolve) {

        Queue<Table> queue = new LinkedList<>();
        Set<Table> visited = new HashSet<>();

        fillAdjacencyList(chartToSolve);

        visited.add(chartToSolve);
        queue.add(chartToSolve);

        while (!queue.isEmpty()) {
            Table currentChart = queue.poll();

            if (Helper.verify(currentChart)) {
                logger.info(currentChart.getSteps());
                logger.info(currentChart.getStepsCount());
                return true;
            }

            for (Table neighbour : adjacencyList.get(currentChart)) {
                if (!visited.contains(neighbour)) {
                    fillAdjacencyList(neighbour);
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        return false;
    }
}