package pl.firaanki;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class BFS {

    private final Map<Table, List<Table>> adjacencyList = new HashMap<>();

    private final char[] order = new char[4];

    Logger logger = Logger.getLogger(getClass().getName());

    private final List<String> results = new ArrayList<>();

    BFS(char[] order) {
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

    public boolean solve(Table chartToSolve) {
        Instant start = Instant.now();
        Queue<Table> queue = new LinkedList<>();
        Set<Table> visited = new HashSet<>();
        int maxDepthRecursion = 0;

        fillAdjacencyList(chartToSolve);

        visited.add(chartToSolve);
        queue.add(chartToSolve);

        while (!queue.isEmpty()) {
            Table currentChart = queue.poll();

            if (Integer.parseInt(currentChart.getStepsCount()) > maxDepthRecursion) {
                maxDepthRecursion = Integer.parseInt(currentChart.getStepsCount());
            }
            if (Helper.verify(currentChart)) {
                Instant stop = Instant.now();
                long timeElapsed = Duration.between(start, stop).toMillis();
                prepareResults(currentChart,
                        String.valueOf(timeElapsed),
                        String.valueOf(visited.size() - queue.size()),
                        String.valueOf(visited.size()),
                        String.valueOf(maxDepthRecursion)
                );
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

    public List<String> getResults() {
        return results;
    }
}