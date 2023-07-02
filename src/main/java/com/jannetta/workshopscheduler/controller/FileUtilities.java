package com.jannetta.workshopscheduler.controller;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtilities {

    public static Vector<Vector<String>> readData(File csv_data, String startTime) {
        try {
            Vector<Vector<String>> data = new Vector<>();
            System.out.println(csv_data);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = df.parse(startTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            String previousData = "0";
            int start = 0;
            Scanner fileScanner = new Scanner(csv_data);
            while (fileScanner.hasNext()) {
                calendar.add(Calendar.MINUTE, Integer.valueOf(previousData));
                startTime = df.format(calendar.getTime());
                String line = startTime + "," + fileScanner.nextLine();
                System.out.println(line);
                String[] tokens = (line).split(",");
                Vector<String> row = new Vector<>(List.of(tokens));
                previousData = tokens[1];
                data.add(row);
            }

            return data;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("Error in Parsing CSV File");
            throw new RuntimeException(e);
        }
    }


    public static void createHTML(Vector<Vector> data, String startTime, File htmlFile) {
        try {
            StringBuilder htmlOuput = new StringBuilder("<head>" +
                    "<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">" +
                    "</head><body>" +
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
            for (int row = 0; row < data.size(); row++) {
                String breakStyle = "";
                String[] tokens = (String[]) data.get(row).toArray(new String[data.get(row).size()]);
                if (tokens[2].trim().equals("BREAK") || tokens[2].trim().equals("LUNCH"))
                    breakStyle = " class=\"break\" ";
                htmlOuput.append("<tr" + breakStyle + ">");
                htmlOuput.append("<td class=\"colstart\" >" + previousData + "</td><td class=\"colduration\">" + tokens[1] + "</td>");
                calendar.add(Calendar.MINUTE, Integer.valueOf(tokens[1]));
                previousData = df.format(calendar.getTime());
                htmlOuput.append("<td class=\"colend\">" + previousData + "</td><td class=\"colepisode\"><a href=\"" + tokens[4] + "\">" + tokens[2] + "</a></td>");
                htmlOuput.append("</tr>");
                htmlOuput.append("</tr>");
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

    public static void saveCsvFile(String filename, Vector<Vector> data) {
        try {
            if (!(filename.endsWith(".csv"))) {
                filename += ".csv";
            }
            FileWriter fileWriter = new FileWriter(filename);
            for (int row = 0; row < data.size(); row++) {
                Vector<String> columns = data.get(row);
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

