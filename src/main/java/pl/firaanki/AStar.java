package pl.firaanki;

import java.util.*;
import java.util.logging.Logger;

public class AStar {

    private final char[] order = {'L', 'R', 'U', 'D'};
    private final Map<Table, List<Table>> adjacencyList = new HashMap<>();

    boolean metrics = false;
    Logger logger = Logger.getLogger(getClass().getName());

    AStar(String metrics) {
        if ("manhattan".equals(metrics)) {
            this.metrics = false;
        } else if ("hamming".equals(metrics)) {
            this.metrics = true;
        } else {
            logger.info("metrics default: manhattan");
        }
    }

    void addEdge(Table parent, Table child) {
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

    /*
    f - g + h
    g - starting point to given position
    h - given position to final destination

    metrics:
        Hamming - count of every number not in place
        Manhattan - sum of count of steps for each number to be in place
     */

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
            return null;
        }
        return Collections.min(set);
    }

    public boolean solve(Table chartToSolve) {

        Map<Double, Table> openList = new HashMap<>();
        Map<Double, Table> closedList = new HashMap<>();

        openList.put(0.0, chartToSolve);

        while (!openList.isEmpty()) {
            Table currentChart = openList.get(getMinFromSet(openList.keySet()));

            if (Helper.verify(currentChart)) {
                logger.info(currentChart.getSteps());
                logger.info(currentChart.getStepsCount());
                return true;
            }

            fillAdjacencyList(currentChart);

            for (Table neighbour : adjacencyList.get(currentChart)) {
                double distance = getDistance(neighbour);

                if (getMinFromSet(getKeys(openList, neighbour)) > distance) {
                    continue;
                }

                if (!closedList.isEmpty()
                        && (getMinFromSet(getKeys(closedList, neighbour)) > distance)) {

                    openList.put(distance, neighbour);

                }
            }

            closedList.put(getDistance(currentChart), currentChart);
        }

        return false;
    }

    Double getDistance(Table table) {
        return metrics ? table.getManhattanValue() : table.getHammingValue();
    }

}
