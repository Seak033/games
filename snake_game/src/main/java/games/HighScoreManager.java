package games;

import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String HIGHSCORE_FILE = "highscore.txt";
    private static final int MAX_ENTRIES = 5;

    public static List<Integer> loadHighscore() {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {

                }
            }
        } catch (IOException e) {

        }

        scores.sort(Collections.reverseOrder());
        return scores.size() > MAX_ENTRIES ? scores.subList(0, MAX_ENTRIES) : scores;
    }

    public static void saveHighscore(int newScore) {
        List<Integer> scores = loadHighscore();
        scores.add(newScore);
        scores.sort(Collections.reverseOrder());
        if (scores.size() > MAX_ENTRIES) {
            scores = scores.subList(0, MAX_ENTRIES);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORE_FILE))) {
            for (int score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetHighscores() {
        try {
            PrintWriter writer = new PrintWriter("highscore.txt");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
