import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class CSVReaderScanner {

  public void csvReaderMethod1(String filePath, String startTime){
    System.out.println(startTime);

//    try {
//      FileWriter myWriter = new FileWriter("filename.html");
//      myWriter.write("This is a file");
//      myWriter.close();
//      System.out.println("Successfully wrote to the file.");
//    } catch (IOException e) {
//      System.out.println("An error occurred.");
//      e.printStackTrace();
//    }

    try {
      Scanner scanner = new Scanner(new File(filePath));
      String test = new String("<table>" +
          "    <tr>" +
          "        <th>Duration (minutes)</th>" +
          "        <th>Index</th>" +
          "        <th>Episode Name</th>" +
          "        <th>Summary</th>" +
          "        <th>URL</th>" +
          "    </tr>\n"
      );


//      startTime
      SimpleDateFormat df = new SimpleDateFormat("HH:mm");
      Date d = df.parse(startTime);
      Calendar cal = Calendar.getInstance();
      cal.setTime(d);


//      System.out.println();

      scanner.useDelimiter("\n");
      while (scanner.hasNext()){
        String[] row = scanner.next().split(",");

        test += "<tr>";

        for(int i=0; i< row.length; i++)
        {
          test += "<td>";

          if (row[i].contains("https")) {
            test += "<a target='_blank' href='";
            test += row[i];
            test += "'>click here for more info</a>";
          } else if (i == 0 && row[i] != "") {

//            startTime + row[i]
//            test += row[i];
            String duration = new String(row[i]);
            int number = Integer.parseInt(duration);
//            System.out.println(number);


            cal.add(Calendar.MINUTE, number);
            String newTime = df.format(cal.getTime());
            test += newTime;

            System.out.println(newTime);


          } else {
            test += row[i];
          }

          test += "</td>";
        }

        test += "</tr>\n";


        // System.out.println(scanner.next()+ " ");
      }
//      startTime
      test += "</table>";
      // System.out.println(test);
      FileWriter myWriter = new FileWriter("lesson2.html");
      myWriter.write(test);
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
    String startTime = new String("09:00");
    CSVReaderScanner csvObj = new CSVReaderScanner();

    csvObj.csvReaderMethod1(filePath, startTime);

  }

}



