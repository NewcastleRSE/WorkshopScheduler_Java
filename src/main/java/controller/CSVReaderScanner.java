package controller;

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

  private void csvReaderMethod1(String filePath, String startTime){

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
          "        <th>Index</th>" +
          "        <th>Episode Name</th>" +
          "        <th>Summary</th>" +
          "        <th>URL</th>" +
          "    </tr>"
      );

      SimpleDateFormat df = new SimpleDateFormat("HH:mm");
      Date d = df.parse(startTime);
      Calendar cal = Calendar.getInstance();
      cal.setTime(d);

      String previousData = new String(df.format(cal.getTime()));


      scanner.useDelimiter("\n");
      while (scanner.hasNext()){
        String[] row = scanner.next().split(",");

        htmlOuput += "<tr>";

        for(int i=0; i< row.length; i++)
        {
          htmlOuput += "<td>";

          if (row[i].contains("https")) {
            htmlOuput += "<a target='_blank' href='";
            htmlOuput += row[i];
            htmlOuput += "'>More info</a>";
          } else if (i == 0) {
              htmlOuput += previousData;
              htmlOuput += "<td>";
              htmlOuput += row[i];
              htmlOuput += "</td>";

                String duration = new String(row[i]);
            int number = Integer.parseInt(duration);

            cal.add(Calendar.MINUTE, number);
            previousData = df.format(cal.getTime());
          } else {
            htmlOuput += row[i];
          }

          htmlOuput += "</td>";
        }

        htmlOuput += "</tr>";

      }
      htmlOuput += "</table>";
      FileWriter myWriter = new FileWriter("lesson2.html");
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

  public static void main(String filePath) {
    JOptionPane.showMessageDialog(null, filePath);
//    String filePath = new File("").getAbsolutePath() + File.separator + "/data/lesson2.csv";
    String startTime = new String("10:00");
    CSVReaderScanner csvObj = new CSVReaderScanner();

    csvObj.csvReaderMethod1(filePath, startTime);
  }
}



