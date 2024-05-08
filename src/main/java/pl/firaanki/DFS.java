package pl.firaanki;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class DFS {

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

    public boolean solve(Table chartToSolve) {
        Instant start = Instant.now();
        Deque<Table> stack = new LinkedList<>();
        HashMap<Table, Integer> visited = new HashMap<>();
        int maxDepthRecursion = 0;

        stack.push(chartToSolve);

        while (!stack.isEmpty()) {
            Table currentChart = stack.pop();
            int currentDepth = currentChart.getSteps().length();

            if (currentDepth > maxDepthRecursion) {
                maxDepthRecursion = currentDepth;
            }

            if (currentChart.verify()) {
                Instant stop = Instant.now();
                long timeElapsed = Duration.between(start, stop).toMillis();

                //---to solution file: count and steps------
                results.add(currentChart.getStepsCount());
                results.add(currentChart.getSteps());

                //----to stats file----------------
                results.add(String.valueOf(visited.size() - stack.size()));
                results.add(String.valueOf(visited.size()));
                results.add(String.valueOf(maxDepthRecursion));
                results.add(String.valueOf(timeElapsed));
                return true;
            }

            visited.put(currentChart, currentChart.getSteps().length());

            if (currentDepth < depth) {
                for (int i = 3; i >= 0; i--) {
                    Table child = currentChart.moveTile(order[i]);

                    if (child != null) {
                        Integer checkDepth = visited.get(child);
                        if (checkDepth == null || checkDepth > child.getStepsCount().length()) {
                            maxDepthRecursion = Math.max(maxDepthRecursion, child.getStepsCount().length());
                            stack.push(child);
                        }
                    }
                }
            }

        }
        return false;
    }

    // podwójny warunek:
    // jeśli chart się powtarza i jeśli ilość kroków do tego stanu jest dłuższa niż już znaleziony
//    private boolean containsChart(Map<Table, Integer> visited, Table currentChart) {
//        for (Table t : ) {
//            if (isEqualChart(currentChart.getChart(), t.getChart())
//                    &&  currentChart.getSteps().length() >= t.getSteps().length()) {
//
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean isEqualChart(int[][] chart1, int[][] chart2) {
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (chart1[i][j] != chart2[i][j]) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

}

/*
                logger.info(
                        Arrays.deepEquals(currentChart.getChart(), t.getChart()) +
                        "\ncurrent: " + currentChart.getStepsCount()
                        + "\nt: " + t.getStepsCount());
 */