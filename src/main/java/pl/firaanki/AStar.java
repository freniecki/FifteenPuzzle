package pl.firaanki;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class AStar {

    private final char[] order = {'L', 'R', 'U', 'D'};

    boolean metrics = false;
    Logger logger = Logger.getLogger(getClass().getName());
    private final List<String> results = new ArrayList<>();

    AStar(String metrics) {
        if ("manh".equals(metrics)) {
            this.metrics = true;
        } else if ("hamm".equals(metrics)) {
            this.metrics = false;
        } else {
            logger.info("default metrics: hamming");
        }
    }

    public boolean solve(Table chartToSolve) {
        Instant start = Instant.now();

        PriorityQueue<Table> priorityQueue = new PriorityQueue<>(new CustomComparator());
        List<Table> closedList = new ArrayList<>();

        priorityQueue.add(chartToSolve);
        int visitedStates = 0;
        int maxDepthRecursion = 0;

        while (!priorityQueue.isEmpty()) {
            Table currentChart = priorityQueue.poll();
            visitedStates++;
            logger.info(currentChart.toString());

            if (Integer.parseInt(currentChart.getStepsCount()) > maxDepthRecursion) {
                maxDepthRecursion = Integer.parseInt(currentChart.getStepsCount());
            }

            if (Helper.verify(currentChart)) {
                Instant stop = Instant.now();
                long timeElapsed = Duration.between(start, stop).toMillis();
                prepareResults(currentChart,
                        String.valueOf(timeElapsed),
                        String.valueOf(visitedStates),
                        String.valueOf(closedList.size()),
                        String.valueOf(maxDepthRecursion)
                );
                return true;
            }

            closedList.add(currentChart);

            for (int i = 0; i < 4; i++) {
                Table neighbour = currentChart.moveTile(order[i]);

                if (neighbour != null) {
                    neighbour.setDistance(getDistance(chartToSolve, neighbour));
                    priorityQueue.add(neighbour);
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

    List<String> getResults() {
        return results;
    }

    Double getDistance(Table current, Table neighbour) {
        return metrics ? getManhattanStart(current, neighbour) + neighbour.getManhattanEnd()
                : getHammingStart(current, neighbour) + neighbour.getHammingEnd();
    }

    double getManhattanStart(Table current, Table neighbour) {
        int[][] currentChart = current.getChart();
        int[][] neighbourChart = neighbour.getChart();
        double manhattan = 0.0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currentChart[i][j] != neighbourChart[i][j]) {
                    int xBasePosition = neighbour.getXPosition(currentChart[i][j]);
                    int yBasePosition = neighbour.getYPosition(currentChart[i][j]);
                    manhattan += (double) Math.abs(i - xBasePosition) + (double) Math.abs(j - yBasePosition);
                }
            }
        }

        return manhattan;
    }

    double getHammingStart(Table current, Table neighbour) {
        int[][] tab1 = current.getChart();
        int[][] tab2 = neighbour.getChart();
        double hamming = 0.0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tab1[i][j] != tab2[i][j]) {
                    hamming++;
                }
            }
        }
        return hamming;
    }
}
