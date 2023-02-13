package controller;

import model.Episode;
import model.Lesson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public Lesson readLessonCSV(String filename) {
        Lesson lesson = new Lesson();
        String path = filename;
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {

                String[] row = line.split(",");
                Episode episode = new Episode(Integer.parseInt(row[0]), row[1], row[2], row[3]);
                lesson.add(episode);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return lesson;
    }
}
