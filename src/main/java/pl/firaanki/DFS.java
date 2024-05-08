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

    List<String> getResults() {
        return results;
    }

    // TODO: ruch wahadłowy ??
    void fillAdjacencyList(Table parent) {
        adjacencyList.putIfAbsent(parent, new ArrayList<>());
        for (int i = 0; i < 4; i++) {
            Table child = parent.moveTile(order[i]);
            if (child != null) {
                adjacencyList.get(parent).add(child);
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

            if (containsChart(visited, currentChart)) {
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

            visited.add(currentChart);

            if (currentDepth >= depth) {
                continue;
            }

            for (Table neighbour : adjacencyList.get(currentChart).reversed()) {
                if (!containsChart(visited, neighbour)) {
                    fillAdjacencyList(neighbour);
                    stack.push(neighbour);
                }
            }
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

    // podwójny warunek:
    // jeśli chart się powtarza i jeśli ilość kroków do tego stanu jest dłuższa niż już znaleziony
    private boolean containsChart(Set<Table> visited, Table currentChart) {
        for (Table t : visited) {
            if (Arrays.deepEquals(currentChart.getChart(), t.getChart())
                    &&  Integer.parseInt(currentChart.getStepsCount()) >= Integer.parseInt(t.getStepsCount())) {
                return true;
            }
        }
        return false;
    }

}
