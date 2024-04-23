package pl.firaanki;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);

        if (args.length != 5) {
            logger.warning("Program requires 5 arguments");
            String message = "Proper usage:" +
                    "1) dfs/bfs/astar" + "2) LRUD(variations)/manh/hamm" +
                    "3) starting state, eg. 4x4_01_0001.txt" +
                    "4) solution file, eg. 4x4_01_0001_bfs_rdul_sol.txt" +
                    "5) statistics file, eg. 4x4_01_0001_bfs_rdul_stats.txt";
            logger.info(message);
            return;
        }
        String strategy = args[0];
        String mode = args[1];
        String sourceFile = args[2];
        String solutionFile = args[3];
        String statsFile = args[4];

        if (!isStrategyCorrect(strategy)) {
            logger.warning("first argument is not correct");

        } else if (strategy.equals("astar")) {
            if (!Objects.equals(mode, "manh") && !Objects.equals(mode, "hamm")) {
                logger.info("mode for astar is not correct");
            } else {
                runAStar(mode, sourceFile, solutionFile, statsFile);
            }
        } else if (strategy.equals("dfs")) {
            if (!isModeCorrect(mode)) {
                logger.info("order for dfs is not correct");
            } else {
                runDFS(mode, sourceFile, solutionFile, statsFile);
            }
        } else {
            if (!isModeCorrect(mode)) {
                logger.info("order for bfs is not correct");
            } else {
                runBFS(mode, sourceFile, solutionFile, statsFile);
            }
        }
    }

    static void runBFS(String mode, String sourceFile, String solutionFile, String statsFile) {
        char[] order = mode.toCharArray();
        Table chartToSolve = FileDaoFactory.getFile(sourceFile).read();
        BFS bfs = new BFS(order);
        boolean isSolved = bfs.solve(chartToSolve);

        if (!isSolved) {
            logger.info("BFS failed!");

            List<String> resultSolution = new ArrayList<>();
            resultSolution.add("-1");
            FileDaoFactory.getFile(solutionFile).write(resultSolution);

            resultSolution.addAll(bfs.getResults().subList(2,5));
            FileDaoFactory.getFile(statsFile).write(resultSolution);
        } else {
            logger.info("BFS succeeded!");
            List<String> results = bfs.getResults();
            FileDaoFactory.getFile(solutionFile).write(results.subList(0, 2));
            results.remove(1);
            FileDaoFactory.getFile(statsFile).write(results);
        }
    }

    static void runDFS(String mode, String sourceFile, String solutionFile, String statsFile) {
        char[] order = mode.toCharArray();
        Table chartToSolve = FileDaoFactory.getFile(sourceFile).read();
        DFS dfs = new DFS(order, 20);
        boolean isSolved = dfs.solve(chartToSolve);

        if (!isSolved) {
            logger.info("DFS failed!");

            List<String> resultSolution = new ArrayList<>();
            resultSolution.add("-1");
            FileDaoFactory.getFile(solutionFile).write(resultSolution);

            resultSolution.addAll(dfs.getResults().subList(2,5));
            FileDaoFactory.getFile(statsFile).write(resultSolution);
        } else {
            logger.info("DFS succeeded!");
            List<String> results = dfs.getResults();
            FileDaoFactory.getFile(solutionFile).write(results.subList(0, 2));
            results.remove(1);
            FileDaoFactory.getFile(statsFile).write(results);
        }
    }

    static void runAStar(String mode, String sourceFile, String solutionFile, String statsFile) {
        AStar aStar = new AStar(mode);
        Table chartToSolve = FileDaoFactory.getFile(sourceFile).read();
        boolean isSolved = aStar.solve(chartToSolve);
        if (!isSolved) {
            logger.info("A* failed!");

            List<String> result = new ArrayList<>();
            result.add("-1");
            FileDaoFactory.getFile(solutionFile).write(result);

            result.addAll(aStar.getResults().subList(2,5));
            FileDaoFactory.getFile(statsFile).write(result);
        } else {
            logger.info("A* succeeded!");
            List<String> results = aStar.getResults();
            FileDaoFactory.getFile(solutionFile).write(results.subList(0, 2));
            results.remove(1);
            FileDaoFactory.getFile(statsFile).write(results);
        }
    }

    static boolean isStrategyCorrect(String strategy) {
        String[] strategies = {"bfs", "dfs", "astar"};
        for(String s : strategies) {
            if (s.equals(strategy)) {
                return true;
            }
        }
        return false;
    }

    static boolean isModeCorrect(String mode) {
        String[] orders = {
                "RDUL",
                "RDLU",
                "DRUL",
                "DRLU",
                "LUDR",
                "LURD",
                "ULDR",
                "ULRD"
        };
        for (String order : orders) {
            if (order.equals(mode)) {
                return true;
            }
        }
        return false;
    }
}
