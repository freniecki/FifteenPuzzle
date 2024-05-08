package pl.firaanki;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static pl.firaanki.Main.logger;

public class DFS {

    private final char[] order = new char[4];

    private final int depth;

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

            //logger.info("current: " + currentChart.toString());

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
                results.add(String.valueOf(visited.size()));
                results.add(String.valueOf(visited.size() + stack.size()));
                results.add(String.valueOf(maxDepthRecursion));
                results.add(String.valueOf(timeElapsed));
                return true;
            }

            visited.put(currentChart, currentChart.getSteps().length());

            for (int i = 3; i >= 0; i--) {
                Table child = thatWasProblemWithBigCognitiveComplexity(i, currentDepth, currentChart, visited);
                if (child != null) {
                    maxDepthRecursion = Math.max(maxDepthRecursion, child.getStepsCount().length());
                    stack.push(child);
                    //logger.info("child: " + child.toString());
                }
            }
        }
        return false;
    }

    private Table thatWasProblemWithBigCognitiveComplexity(int orderIndex, int currentDepth, Table currentChart, HashMap<Table, Integer> visited) {
        if (currentDepth < depth) {
            Table child = currentChart.moveTile(order[orderIndex]);

            if (child != null) {
                Integer checkDepth = visited.get(child);
                if (checkDepth == null || checkDepth > child.getStepsCount().length()) {

                    return child;
                }
            }
        }
        return null;
    }
}
