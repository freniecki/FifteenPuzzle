package pl.firaanki;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileDao {

    private final String fileName;

    Logger logger = Logger.getLogger(getClass().getName());

    FileDao(String fileName) {
        this.fileName = fileName;
    }

    public Table read() {
        try {
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            List<String> array = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                array.add(data);
            }

            int xSize = Character.getNumericValue(array.getFirst().charAt(0));
            int ySize = Character.getNumericValue(array.getFirst().charAt(2));

            int[][] chart = new int[xSize][ySize];
            for (int i = 0; i < xSize; i++) {
                String[] string = array.get(i + 1).split(" ");
                for (int j = 0; j < ySize; j++) {
                    chart[i][j] = Integer.parseInt(string[j]);
                }
            }

            myReader.close();

            return new Table(chart, "");

        } catch (FileNotFoundException e) {
            logger.info("raed exception");
        }
        return null;
    }

    public void write(List<String> message) {
        try {
            File file = new File(fileName);

            if (file.createNewFile()) {
                FileWriter myWriter = new FileWriter(fileName);

                while (!message.isEmpty()) {
                    String line = message.getFirst();
                    message.removeFirst();
                    myWriter.write(line);
                }

                myWriter.close();

            } else {
                logger.info("file already exist");
            }


        } catch (IOException e) {
            logger.info("cannot write to file");
        }
    }

}
