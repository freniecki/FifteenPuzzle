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
        stack.push(chartToSolve);

        while (!stack.isEmpty()) {
            Table currentChart = stack.pop();
            int currentDepth = Integer.parseInt(currentChart.getStepsCount());

            if (containsChart(visited, currentChart.getChart())) {
                continue;
            }

//            String stringMessage = currentChart + currentChart.getSteps()
//                    + '\n' + currentChart.getStepsCount();
//            logger.info(stringMessage);

            if (currentDepth > maxDepthRecursion) {
                maxDepthRecursion = currentDepth;
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

            if (currentDepth >= depth) {
                continue;
            }

            for (Table neighbour : adjacencyList.get(currentChart).reversed()) {
                if (neighbour != null && !containsChart(visited, neighbour.getChart())) {
                    fillAdjacencyList(neighbour);
                    stack.push(neighbour);
                }
            }
            visited.add(currentChart);
        }
        return false;
    }

    void prepareResults(Table currentChart, String timeElapsed,
                        String visitedStates, String processedStates, String maxDepthRecursion) {
        //---to solution file: count and steps------
        results.add(currentChart.getStepsCount());
        results.add(currentChart.getSteps());
        //----to stats file----------------
        results.add(visitedStates);
        results.add(processedStates);
        results.add(maxDepthRecursion);
        results.add(timeElapsed);
    }

    List<String> getResults() {
        return results;
    }

    private boolean containsChart(Set<Table> visited, int[][] chart) {
        for (Table t : visited) {
            if (Arrays.deepEquals(t.getChart(), chart)) {
                return true;
            }
        }
        return false;
    }

}
