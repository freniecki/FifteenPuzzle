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
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            List<String> array = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                array.add(data);
            }

            int xSize = Character.getNumericValue(array.getFirst().charAt(0));
            int ySize = Character.getNumericValue(array.getFirst().charAt(2));

            int[][] chart = new int[xSize][ySize];
            for (int i = 0; i < xSize; i++) {
                String string = array.get(i+1);
                for (int j = 0; j < ySize; j++) {
                    chart[i][j] = Integer.parseInt(
                            string.substring(0, array.indexOf(' ')));
                    string
                }
            }

            myReader.close();

            return new Table(chart);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
