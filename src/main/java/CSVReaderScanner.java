import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class CSVReaderScanner {

  public void csvReaderMethod1(String filePath){

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
          "        <th>Duration</th>" +
          "        <th>Index</th>" +
          "        <th>Episode Name</th>" +
          "        <th>URL</th>" +
          "    </tr>\n"
      );

//      test += "Hello";


      scanner.useDelimiter("\n");
      while (scanner.hasNext()){
        String[] row = scanner.next().split(",");

        test += "<tr>";

//        test += scanner.next();

        for(int i=0; i< row.length; i++)
        {
          test += "<td>";

          if (row[i].contains("url")) {
            test += "<a href='";
            test += row[i];
            test += "'>URL</a>";
          } else {
            test += row[i];
          }

          test += "</td>";
        }

        test += "</tr>\n";


        // System.out.println(scanner.next()+ " ");
      }

      test += "</table>";
      // System.out.println(test);
      FileWriter myWriter = new FileWriter("table.html");
      myWriter.write(test);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    String filePath = new File("").getAbsolutePath() + File.separator + "/data/lesson.csv";
    CSVReaderScanner csvObj = new CSVReaderScanner();

    csvObj.csvReaderMethod1(filePath);

  }

}



