package pl.firaanki;

import java.util.*;

public class Model {

    private static final int[][] PATTERN_CHART = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    Map<Table, List<Table>> adjacencyList = new HashMap<>();

    private final char[] order;
    private String solution = "";

    /* --------------------------------------------------------------------- */
    Model(char[] order)  {
        this.order = order;
    }

    String getSolution() {
        return solution;
    }

    void addEdge(Table parent, Table child) {
        adjacencyList.putIfAbsent(parent, new ArrayList<>());
        adjacencyList.putIfAbsent(child, new ArrayList<>());
        adjacencyList.get(parent).add(child);
    }

    void fillAdjacencyList(Table chartToSolve) {
        for (int i = 0; i < 4; i++) {
            addEdge(chartToSolve, chartToSolve.moveTile(order[i]));
        }
    }

    public boolean verify(Table chart) {
        for (int i = 0; i < chart.getX(); i++) {
            for (int j = 0; j < chart.getY(); j++) {
                if (PATTERN_CHART[i][j] != chart.getValue(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifySides(Table chartToMove) {
        boolean check = false;
        for (int i = 0; i < 4; i++) {
            check = verify(chartToMove.moveTile(order[i]));
            if (check) {
                solution += order[i];
            }
        }
        return check;
    }

    public boolean bfs(Table chartToSolve) {

        Queue<Table> queue = new LinkedList<>();
        Set<Table> visited = new HashSet<>();

        fillAdjacencyList(chartToSolve);

        for (Table t : adjacencyList.get(chartToSolve)) {
            printMethod(t);
        }

        visited.add(chartToSolve);
        queue.add(chartToSolve);

        while (!queue.isEmpty()) {
            Table currentChart = queue.poll();

            printMethod(currentChart);

            if (verify(currentChart)) {
                return true;
            }

            for (Table neighbours : adjacencyList.get(currentChart)) {
                if (!visited.contains(neighbours)) {
                    visited.add(neighbours);
                    queue.add(neighbours);
                }
            }
        }

        return false;
    }

    public void printMethod(Table chart) {
        for (int i = 0; i < chart.getX(); i++) {
            for (int j = 0; j < chart.getY(); j++) {
                System.out.print(chart.getValue(i,j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}