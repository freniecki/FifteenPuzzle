package pl.firaanki;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class DFS {

    private final Map<Table, List<Table>> adjacencyList = new HashMap<>();

    private final char[] order = new char[4];

    private final int depth;

    Logger logger = Logger.getLogger(getClass().getName());

    private final List<String> results = new ArrayList<>();

    DFS(char[] order, int depth) {
        System.arraycopy(order, 0, this.order, 0, 4);
        this.depth = depth;
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

    public boolean solve(Table chartToSolve) {
        Instant start = Instant.now();
        Deque<Table> stack = new LinkedList<>();
        Set<Table> visited = new HashSet<>();
        int maxDepthRecursion = 0;

        fillAdjacencyList(chartToSolve);

        visited.add(chartToSolve);
        stack.push(chartToSolve);

        while (!stack.isEmpty()) {
            Table currentChart = stack.pop();

            if (containsChart(currentChart.getChart())) {
                logger.info("i fcked up");
                return false;
            }

            if (Integer.parseInt(currentChart.getStepsCount()) > depth) {
                continue;
            }

            if (Integer.parseInt(currentChart.getStepsCount()) > maxDepthRecursion) {
                maxDepthRecursion = Integer.parseInt(currentChart.getStepsCount());
            }

            if (Helper.verify(currentChart)) {
                Instant stop = Instant.now();
                long timeElapsed = Duration.between(start, stop).toMillis();
                prepareResults(currentChart,
                        String.valueOf(timeElapsed),
                        String.valueOf(visited.size() - stack.size()),
                        String.valueOf(visited.size()),
                        String.valueOf(maxDepthRecursion)
                );
                return true;
            }

            for (Table neighbour : adjacencyList.get(currentChart).reversed()) {
                if (!visited.contains(neighbour)) {
                    fillAdjacencyList(neighbour);
                    visited.add(neighbour);
                    stack.push(neighbour);
                }
            }
        }

        return false;
    }

    void prepareResults(Table currentChart, String timeElapsed,
                        String visitedStates, String processedStates, String maxDepthRecursion) {
        //---to solution file: count and steps------
        results.add(currentChart.getSteps());
        results.add(currentChart.getStepsCount());
        //----to stats file----------------
        results.add(visitedStates);
        results.add(processedStates);
        results.add(maxDepthRecursion);
        results.add(timeElapsed);
    }

    List<String> getResults() {
        return results;
    }

    private boolean containsChart(int[][] chart) {
        for (Table t : adjacencyList.keySet()) {
            if (t.getChart() == chart) {
                return true;
            }
        }
        return false;
    }


}
