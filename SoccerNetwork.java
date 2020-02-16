/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soccernetwork;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eqiu
 */
public class SoccerNetwork {

    /**
     * @param args the command line arguments
     */
    static List<List<String>> originalFile = new ArrayList<>();
    static ArrayList<String> detection = new ArrayList<>();
    static ArrayList<Integer> formation = new ArrayList<>();

    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<Player> friendlyStorage = new ArrayList<>();
    static ArrayList<Player> opposingStorage = new ArrayList<>();

    public static boolean clean() {
        for (String s : detection)
            if (detection.size() > 2) {
                System.out.println("FORMATION DETECTED " + detection.size() / 2);
                return true;
            } else
                return false;
        return false;
    }

    public static void reset() {
        friendlyStorage.clear();
        opposingStorage.clear();
    }

    public static int findMyFriendlyPlayer(String name) {
        int i = 0;
        while (i < friendlyStorage.size()) {
            if (friendlyStorage.get(i).getName().equals(name))
                return i;
            i++;
        }
        return -5;
    }

    public static int findMyOpposingPlayer(String name) {
        int i = 0;
        while (i < opposingStorage.size()) {
            if (opposingStorage.get(i).getName().equals(name))
                return i;
            i++;
        }
        return -5;
    }

    public static void main(String[] args) throws Exception {

        Reader.readFile();
        originalFile = Reader.getLines();
        //int lineNo = 0;

        int match = 0;
        //create all the players first
        for (List<String> l : originalFile)
            //
            if (l.get(0).equals("2")) {
                if (!names.contains(l.get(2)))
                    names.add(l.get(2));
                if (!names.contains(l.get(3)))
                    names.add(l.get(3));
            }

        for (String s : names)
            if (s.contains("Huskies"))
                friendlyStorage.add(new Player(s));
            else
                opposingStorage.add(new Player(s));

        //what passes am i making?
        double time = 0;
        for (int lineNo = 0; lineNo < originalFile.size(); lineNo++) {
            List<String> line = originalFile.get(lineNo);
            System.out.println(line);

            //making sure we don't go too far
            if (Integer.parseInt(line.get(0)) < 2)
                continue;
            System.out.println("next");

            //rest of logic
            int[] position = new int[2];
            boolean friendly = false;
            if (line.get(1).contains("usk")) {
                friendly = true;
                position[0] = findMyFriendlyPlayer(line.get(2));
                position[1] = findMyFriendlyPlayer(line.get(3));
            } else {
                position[0] = findMyOpposingPlayer(line.get(2));
                position[1] = findMyOpposingPlayer(line.get(3));
            }
            if (position[0] * position[1] <= 0)
                break;

            //looking for the players that made the pass and record it
            try {
                if (friendly) {
                    friendlyStorage.get(position[0]).pass(line.get(6));
                    friendlyStorage.get(position[1]).pass(line.get(6));
                } else {
                    opposingStorage.get(position[0]).pass(line.get(6));
                    opposingStorage.get(position[1]).pass(line.get(6));
                }
            } catch (Exception e) {
            }

            //check if formations is empty, if so add timer
            if (detection.isEmpty())
                time = Double.parseDouble(originalFile.get(lineNo).get(5));

            //adding names to detect formations
            //if (!detection.contains(line.get(2)))
            detection.add(line.get(2));
            //if (!detection.contains(line.get(3)))
            detection.add(line.get(3));
            if (lineNo == 0)
                continue;

            //making sure the logic works as intended
            try {
                if (!line.get(1).equals(originalFile.get(lineNo - 1).get(1)) || !line.get(0).equals(originalFile.get(lineNo - 1).get(0)) || !line.get(4).equals(originalFile.get(lineNo - 1).get(4))) {
                    if (clean()) {
                        int multiplier;
                        if (detection.isEmpty())
                            multiplier = 1;
                        else
                            multiplier = detection.size();
                        if (friendly) {
                            friendlyStorage.get(position[0]).activate(multiplier);
                            friendlyStorage.get(position[1]).activate(multiplier);
                        } else {
                            opposingStorage.get(position[0]).activate(multiplier);
                            opposingStorage.get(position[1]).activate(multiplier);
                        }
                    }
                    detection.clear();
                }

            } catch (Exception e) {
            }

            //throtling time
            if (Double.parseDouble(line.get(5)) - time > 10) {
                if (clean()) {
                    int multiplier;
                    if (detection.isEmpty())
                        multiplier = 1;
                    else
                        multiplier = detection.size();
                    if (friendly) {
                        friendlyStorage.get(position[0]).activate(multiplier);
                        friendlyStorage.get(position[1]).activate(multiplier);
                    } else {
                        opposingStorage.get(position[0]).activate(multiplier);
                        opposingStorage.get(position[1]).activate(multiplier);
                    }
                }
                detection.clear();
            }
            System.out.println(detection);
            int columnNo = 0;
            //detection.clear();

        }

        for (int i = 0; i < friendlyStorage.size(); i++) {
            friendlyStorage.get(i).activate(1);
            System.out.println(friendlyStorage.get(i).printResult());
        }

        for (int i = 0; i < opposingStorage.size(); i++) {
            opposingStorage.get(i).activate(1);
            System.out.println(opposingStorage.get(i).printResult());
        }
    }

}
/*
for (int lineNo = 1; lineNo < originalFile.size(); lineNo++) {
            List<String> line = originalFile.get(lineNo);
            System.out.println(line);
            detection.add(line.get(2));
            detection.add(line.get(3));
            if (!line.get(1).equals(originalFile.get(lineNo - 1).get(1)) || !line.get(0).equals(originalFile.get(lineNo - 1).get(0)) || !line.get(4).equals(originalFile.get(lineNo - 1).get(4))) {
                if (clean())
                    formation.add(lineNo);
                if (line.get(1).contains("_"))
                    throw new Exception(detection.get(1) + "  " + String.valueOf(lineNo));
                detection.clear();
                continue;
            }
            int columnNo = 0;
            //if(line.get(0))
            for (String value : line)

                columnNo++;

        }
        System.out.println(formation.isEmpty());
        for (int i : formation)
            System.out.println(i);
 */
