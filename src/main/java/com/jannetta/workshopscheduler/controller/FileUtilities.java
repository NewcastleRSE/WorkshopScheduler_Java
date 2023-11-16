package com.jannetta.workshopscheduler.controller;

import com.jannetta.workshopscheduler.model.Schedule;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtilities {

    public static Schedule readData(File csv_data) {
        try {
            Vector<Vector<String>> data = new Vector<>();
            System.out.println(csv_data);

            String previousData = "0";
            Scanner fileScanner = new Scanner(csv_data);
            String title = fileScanner.nextLine().split(",")[1];
            String calcTime = fileScanner.nextLine().split(",")[1];
            String startTime = calcTime;
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = df.parse(calcTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            while (fileScanner.hasNext()) {
                calendar.add(Calendar.MINUTE, Integer.parseInt(previousData));
                calcTime = df.format(calendar.getTime());
                String line = calcTime + "," + fileScanner.nextLine();
                String[] tokens = (line).split(",");
                Vector<String> row = new Vector<>(List.of(tokens));
                previousData = tokens[1];
                data.add(row);
            }
            Schedule schedule = new Schedule(title, startTime, data);
            return schedule;
        } catch (FileNotFoundException e) {
            System.out.println("File " + csv_data + " not found");
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("Error in Parsing CSV File");
            throw new RuntimeException(e);
        }
    }


    /**
     * Export the schedule to HTML for inclusion in the website
     * @param data the schedule as a Vector of Vectors
     * @param startTime the start time of the workshop
     * @param htmlFile the file to which the HTML should be written to
     */
    public static void createHTML(Vector<Vector> data, String startTime, File htmlFile) {
        try {
            StringBuilder htmlOuput = new StringBuilder(
                    "<table class=\"styled_table\">" +
                    "    <tr>" +
                    "        <th>Start</th>" +
                    "        <th>Duration (minutes)</th>" +
                    "        <th>End</th>" +
                    "        <th>Episode</th>" +
                    "    </tr>");
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = df.parse(startTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            String previousData = df.format(calendar.getTime());

            boolean hasError = false;
            for (Vector datum : data) {
                String breakStyle = "";
                String[] tokens = (String[]) datum.toArray(new String[datum.size()]);
                if (tokens[2].trim().equals("BREAK") || tokens[2].trim().equals("LUNCH"))
                    breakStyle = " class=\"break\" ";
                htmlOuput.append("<tr" + breakStyle + ">");
                htmlOuput.append("<td class=\"colstart\" >" + previousData + "</td><td class=\"colduration\">" + tokens[1] + "</td>");
                calendar.add(Calendar.MINUTE, Integer.parseInt(tokens[1]));
                previousData = df.format(calendar.getTime());
                htmlOuput.append("<td class=\"colend\">" + previousData + "</td><td class=\"colepisode\"><a href=\"" + tokens[4] + "\">" + tokens[2] + "</a></td>");
                htmlOuput.append("</tr>" + "\n");
            }
            htmlOuput.append("<tr><td>" + previousData + "</td><td>Finish</td><td></td><td></td></tr>");
            if (hasError) {
                JOptionPane.showMessageDialog(null, "Attention: You have episodes in your lesson without duration. The start time cannot be calculated. Click OK to continue.");
            }
            htmlOuput.append("</table>");
            FileWriter myWriter = new FileWriter(htmlFile);
            myWriter.write(htmlOuput.toString());
            myWriter.close();
            System.out.println("Successfully wrote to file: " + htmlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save the schedule to a CSV file
     * @param filename the name of the file to which the data should be written
     * @param startTime the start time of the workshop
     * @param title the title of the workshop
     * @param data the data, as a Vector of Vectors, to be written, in CSV format, to the file
     */
    public static void saveCsvFile(String filename, String startTime, String title, Vector<Vector> data) {
        try {
            if (!(filename.endsWith(".csv"))) {
                filename += ".csv";
            }
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("title," + title + "\n");
            fileWriter.write("time," + startTime + "\n");
            for (Vector<String> columns : data) {
                String line = String.join(",", columns);
                // remove first column which holds the start time and is calculated on each load
                line = line.substring(line.indexOf(",") + 1);
                fileWriter.write(line + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

