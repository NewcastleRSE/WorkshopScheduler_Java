package com.jannetta.workshopscheduler.controller;

import javax.swing.*;
import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CSVReaderScanner {


    private void createHTML(String csvFile, String startTime, String htmlFile) {

        try {
            Scanner scanner = new Scanner(new File(csvFile));
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
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String previousData = df.format(cal.getTime());
            scanner.useDelimiter("\n");

            boolean hasError = false;
            while (scanner.hasNext()) {
                String breakStyle = "";
                String[] tokens = scanner.next().split(",");
                if (tokens[1].trim().equals("BREAK") || tokens[1].trim().equals("LUNCH"))
                    breakStyle = " class=\"break\" ";
                htmlOuput.append("<tr" + breakStyle + ">");
                htmlOuput.append("<td class=\"colstart\" >" + previousData + "</td><td class=\"colduration\">" + tokens[0] + "</td>");
                cal.add(Calendar.MINUTE, Integer.valueOf(tokens[0]));
                previousData = df.format(cal.getTime());
                htmlOuput.append("<td class=\"colend\">" + previousData + "</td><td class=\"colepisode\"><a href=\"" + tokens[3] + "\">" + tokens[1] + "</a></td>");
                htmlOuput.append("</tr>");
                htmlOuput.append("</tr>");
            }
            htmlOuput.append("<tr><td>" + previousData + "</td><td>Finish</td><td></td><td></td></tr>");
            if (hasError) {
                JOptionPane.showMessageDialog(null, "Attention: You have episodes in your lesson without duration. The start time cannot be calculated. Click OK to continue.");
            }
            htmlOuput.append("</table>");
            FileWriter myWriter = new FileWriter(htmlFile + ".html");
            myWriter.write(htmlOuput.toString());
            myWriter.close();
            System.out.println("Successfully wrote to file: " + htmlFile + ".html");
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String filePath, String startTime, String fileName) {
//    JOptionPane.showMessageDialog(null, filePath);
//    String filePath = new File("").getAbsolutePath() + File.separator + "/data/lesson2.csv";
//    String startTime = new String("10:00");
        CSVReaderScanner csvObj = new CSVReaderScanner();

        csvObj.createHTML(filePath, startTime, fileName);
    }
}



