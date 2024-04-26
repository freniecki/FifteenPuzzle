package pl.firaanki;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class AStar {

    private final char[] order = {'L', 'R', 'U', 'D'};
    private final Map<Table, List<Table>> adjacencyList = new HashMap<>();

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

    private void addEdge(Table parent, Table child) {
        adjacencyList.putIfAbsent(parent, new ArrayList<>());
        adjacencyList.putIfAbsent(child, new ArrayList<>());
        adjacencyList.get(parent).add(child);
    }

    private void fillAdjacencyList(Table chartToSolve) {
        for (int i = 0; i < 4; i++) {
            Table neighbour = chartToSolve.moveTile(order[i]);

            if (neighbour != null) {
                addEdge(chartToSolve, neighbour);
            }
        }
    }

    public Set<Double> getKeys(Map<Double, Table> map, Table value) {
        Set<Double> keys = new HashSet<>();
        for (Map.Entry<Double, Table> entry : map.entrySet()) {
            if (Objects.equals(entry.getValue(), value)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public Double getMinFromSet(Set<Double> set) {
        if (set.isEmpty()) {
            return Double.MAX_VALUE;
        }
        return Collections.min(set);
    }

    public boolean solve(Table chartToSolve) {
        Instant start = Instant.now();
        Map<Double, Table> openList = new HashMap<>();
        Map<Double, Table> closedList = new HashMap<>();
        openList.put(0.0, chartToSolve);
        int visitedStates = 0;
        int maxDepthRecursion = 0;

        while (!openList.isEmpty()) {
            Double currentKey = getMinFromSet(openList.keySet());
            Table currentChart = openList.get(currentKey);
            openList.remove(currentKey, currentChart);

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
            closedList.put(currentKey, currentChart);

            fillAdjacencyList(currentChart);

            for (Table neighbour : adjacencyList.get(currentChart)) {
                if (Integer.parseInt(neighbour.getStepsCount()) > maxDepthRecursion) {
                    maxDepthRecursion = Integer.parseInt(neighbour.getStepsCount());
                }

                if (Helper.verify(neighbour)) {
                    Instant stop = Instant.now();
                    long timeElapsed = Duration.between(start, stop).toMillis();
                    prepareResults(neighbour,
                            String.valueOf(timeElapsed),
                            String.valueOf(visitedStates),
                            String.valueOf(closedList.size()),
                            String.valueOf(maxDepthRecursion)
                    );
                    return true;
                }

                fillAdjacencyList(neighbour);
                double distance = getDistance(chartToSolve, neighbour);

                //if state is in openList and has lower distance -> skip neighbour
                //if state is in closedList and has lower distance -> skip neighbour
                if (getMinFromSet(getKeys(openList, neighbour)) < distance
                        || getMinFromSet(getKeys(closedList, neighbour)) < distance) {
                    continue;
                }

                // if distance is lower than in lists or it/'s not in lists -> add neighbour
                openList.put(distance, neighbour);
            }

            closedList.put(getDistance(chartToSolve, currentChart), currentChart);
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
