package pl.firaanki;

import java.util.*;
import java.util.logging.Logger;

public class DFS {

    private final Map<Table, List<Table>> adjacencyList = new HashMap<>();

    private final char[] order = new char[4];

    Logger logger = Logger.getLogger(getClass().getName());

    DFS(char[] order) {
        System.arraycopy(order, 0, this.order, 0, 4);
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

    public boolean dfs(Table chartToSolve) {
        Deque<Table> stack = new LinkedList<>();
        Set<Table> visited = new HashSet<>();

        fillAdjacencyList(chartToSolve);

        visited.add(chartToSolve);
        stack.push(chartToSolve);

        logger.info("base: " + chartToSolve.toString());

        while (!stack.isEmpty()) {
            Table currentChart = stack.pop();

            if (Integer.parseInt(currentChart.getStepsCount()) > 25) {
                continue;
            }

            if (Helper.verify(currentChart)) {
                logger.info(currentChart.getSteps());
                logger.info(currentChart.getStepsCount());
                return true;
            }

            for (Table neighbour : adjacencyList.get(currentChart).reversed()) {
                if (!visited.contains(neighbour)) {

                    logger.info("neighbour: " + neighbour.toString());

                    fillAdjacencyList(neighbour);
                    visited.add(neighbour);
                    stack.push(neighbour);
                }
            }

            logger.info(currentChart.getSteps());
            logger.info(currentChart.getStepsCount());
        }

        return false;
    }


}
