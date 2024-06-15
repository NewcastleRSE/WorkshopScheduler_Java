package com.jannetta.workshopscheduler.controller;

import com.jannetta.workshopscheduler.model.Schedule;

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
            Vector data = new Vector<>();
            System.out.println(csv_data);

            String previousData = "0";
            Scanner fileScanner = new Scanner(csv_data);
            String title = ""; //
            String calcTime = "09:00"; //
            String startTime = ""; //
            String header = "";
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = df.parse(calcTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("title")) {
                    title = line.split(",")[1];
                } else if (line.startsWith("time")) {
                    calcTime = line.split(",")[1];
                    startTime = calcTime;
                    d = df.parse(calcTime);
                    calendar = Calendar.getInstance();
                    calendar.setTime(d);
                } else if (line.startsWith("header")) {
                    header = line.split(",")[1];
                } else {
                    calendar.add(Calendar.MINUTE, Integer.parseInt(previousData));
                    calcTime = df.format(calendar.getTime());
                    line = calcTime + "," + line;
                    String[] tokens = (line).split(",");
                    Vector<String> row = new Vector<>(List.of(tokens));
                    previousData = (tokens[1].isEmpty()) ? "0" : tokens[1];
                    data.add(row);
                }
            }
            return new Schedule(title, startTime, header, data);
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
    public static void createHTML(Vector<Vector> data, String startTime, String header, File htmlFile) {
        String tableHeader = "<div class=\"header\">" + header + "</div>" +
                """
                        <div class="row">
                        <div class="col-md-6">
                        <h3>Day 1</h3>
                        <table class="table table-striped">
                        <tr><th>Start</th><th>Duration (minutes)</th><th>End</th><th>Episode</th></tr>
                        """;
        try {
            int dayCounter = 0;
            StringBuilder htmlOutput = new StringBuilder(tableHeader);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = df.parse(startTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            String previousData = df.format(calendar.getTime());

            for (Vector datum : data) {
                String[] tokens = (String[]) datum.toArray(new String[datum.size()]);
                if (tokens[2].trim().equals("DAY BREAK")) {
                    // close table
                    htmlOutput.append("</table>\n");
                    htmlOutput.append("</div>\n");
                    htmlOutput.append("<div class=\"col-md-6\">\n");
                    dayCounter++;
                    htmlOutput.append("<h3>Day ").append(dayCounter + 1).append("</h3>\n");
                    htmlOutput.append("<table class=\"table table-striped\">\n" +
                            "<tr>" +
                            "<th>Start</th>" +
                            "<th>Duration (minutes)</th>" +
                            "<th>End</th>" +
                            "<th>Episode</th>" +
                            "</tr>\n");
                    d = df.parse(startTime);
                    calendar = Calendar.getInstance();
                    calendar.setTime(d);
                    previousData = df.format(calendar.getTime());
                } else {
                    System.out.printf("%s %s %s %s %s\n", tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
                    if (Utilities.isNumeric(tokens[1])) {
                        calendar.add(Calendar.MINUTE, Integer.parseInt(tokens[1]));
                        previousData = df.format(calendar.getTime());
                    }
                    htmlOutput.append("<tr>\n\t<td>" + tokens[0] + "</td><td>" + tokens[1] + "</td><td>" + previousData + "</td><td><a href=\"" + tokens[4] + "\">" + tokens[2] + "</a></td>\n</tr>\n");
                }
            }
            htmlOutput.append("</table>\n");
            htmlOutput.append("</div>\n</div>\n");

            FileWriter myWriter = new FileWriter(htmlFile);
            myWriter.write(htmlOutput.toString());
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
     * @param header a header that can be printed above the table
     * @param data the data, as a Vector of Vectors, to be written, in CSV format, to the file
     */
    public static void saveCsvFile(String filename, String startTime, String title, String header, Vector<Vector> data) {
        try {
            if (!(filename.endsWith(".csv"))) {
                filename += ".csv";
            }
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("title," + title + "\n");
            fileWriter.write("time," + startTime + "\n");
            fileWriter.write("header," + (header.isEmpty()?"-":header) + "\n");
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

