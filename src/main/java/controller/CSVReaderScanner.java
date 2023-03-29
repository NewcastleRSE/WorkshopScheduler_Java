package controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import view.GUI;

public class CSVReaderScanner{



  private void csvReaderMethod1(String filePath, String startTime, String fileName){

    try {
      Scanner scanner = new Scanner(new File(filePath));
      String htmlOuput = new String("<head>" +
 "<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">" +
"</head><body>" +
          "<h2>Schedule</h2>" +
          "<table class=\"styled_table\">" +
          "    <tr>" +
          "        <th>Start time</th>" +
          "        <th>Duration (minutes)</th>" +
          "        <th>More episode info ...</th>" +
//          "        <th>Episode Name</th>" +
//          "        <th>Summary</th>" +
//          "        <th>URL</th>" +
          "    </tr>"
      );

      SimpleDateFormat df = new SimpleDateFormat("HH:mm");
      Date d = df.parse(startTime);
      Calendar cal = Calendar.getInstance();
      cal.setTime(d);

      String previousData = new String(df.format(cal.getTime()));


      scanner.useDelimiter("\n");

      boolean hasError = false;
      while (scanner.hasNext()){
        String[] row = scanner.next().split(",");

        htmlOuput += "<tr>";

        for(int i=0; i< row.length; i++)
        {
          htmlOuput += "<td>";

          if (row[i].contains("https")) {
            htmlOuput += "<a target='_blank' href='";
            htmlOuput += row[i];
            htmlOuput += "'>Breakdown</a>";
          }
//          else if (row[i] != (int)row[i]) {
//            JOptionPane.showMessageDialog(null, "The first column must be an integer");
//          }
          else if (i == 0) {
              htmlOuput += previousData;
              htmlOuput += "<td>";
              htmlOuput += row[i];
              htmlOuput += "</td>";

//            String duration = new String(row[i]);
//            int number = Integer.parseInt(duration);
//            String numberString = "12345";
            try {
              int number = Integer.parseInt(row[i]);
//              System.out.println("The number is valid: " + number);
//              JOptionPane.showMessageDialog(null, number.getClass().getName());
              cal.add(Calendar.MINUTE, number);
            } catch (NumberFormatException e) {
              hasError = true;
            }

            previousData = df.format(cal.getTime());
          } else {
            htmlOuput += row[i];
          }

          htmlOuput += "</td>";
        }

        htmlOuput += "</tr>";

      }
      if (hasError == true){
        JOptionPane.showMessageDialog(null, "The first column must be an integer");
      }
      htmlOuput += "</table>";
      FileWriter myWriter = new FileWriter(fileName+".html");
      myWriter.write(htmlOuput);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String filePath, String startTime, String fileName) {
//    JOptionPane.showMessageDialog(null, filePath);
//    String filePath = new File("").getAbsolutePath() + File.separator + "/data/lesson2.csv";
//    String startTime = new String("10:00");
    CSVReaderScanner csvObj = new CSVReaderScanner();

    csvObj.csvReaderMethod1(filePath, startTime, fileName);
  }
}



