import java.io.File;
import java.text.DateFormat;
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
      String htmlOuput = new String("<style>" +
          "table {" +
          "border-collapse: collapse;" +
          "border: 1px solid;" +
//          "border-color: #8533ff;" +
          "width: 100%;" +
          "}" +
          "th, td {" +
          "  padding: 8px;" +
          "  text-align: centre;" +
//          "  border-bottom: 1px solid #ddd;\n" +
          "}" +
          "th{" +
          "background-color: #d9d9d9;" +
          "padding: 8px;}" +
          "a {\n" +
          "background-color: #8533ff;\n" +
          "border: none;\n" +
          "border-radius: 12px;" +
          "box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);" +
          "color: white;\n" +
          "padding: 15px 32px;\n" +
          "text-align: center;\n" +
          "text-decoration: none;\n" +
          "display: inline-block;\n" +
          "font-size: 16px;" +
          "}" +
          "tr:hover {background-color: #f2f2f2;}" +
          "</style>" +
          "<body>" +
          "<h2>Schedule</h2>" +
          "<table>" +
          "<table>" +
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
            htmlOuput += "'>Click here for more info</a>";
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

        htmlOuput += "</tr>\n";

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

  public static void main(String[] args) {
    String filePath = new File("").getAbsolutePath() + File.separator + "/data/lesson2.csv";
    String startTime = new String("10:00");
    CSVReaderScanner csvObj = new CSVReaderScanner();

    csvObj.csvReaderMethod1(filePath, startTime);
  }
}



