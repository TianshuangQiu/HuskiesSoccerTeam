package soccernetwork;

import java.util.*;
import java.io.*;

/**
 *
 * @author eqiu
 */
public class Reader {

    static List<List<String>> lines = new ArrayList<>();
    static String fileName = "test.csv";
    static ArrayList<File> csvList = new ArrayList<>();

    public static void readFile() {

        File folder = new File("/Users/eqiu/Desktop/2020");
        System.out.println(folder.exists());
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
            if (file.isFile() && file.getName().endsWith("test.csv"))
                csvList.add(file);

        // this gives you a 2-dimensional array of strings
        Scanner inputStream;
        for (File file : csvList)
            try {
                inputStream = new Scanner(file);
                while (inputStream.hasNext()) {
                    String line = inputStream.nextLine();
                    String[] values = line.split(",");
                    // this adds the currently parsed line to the 2-dimensional string array
                    lines.add(Arrays.asList(values));
                    //System.out.println(values[0]);
                }

                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    public static List<List<String>> getLines() {
        return lines;
    }

}
