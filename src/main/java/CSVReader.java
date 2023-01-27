import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
public class CSVReader {
  private void csvReaderMethod1(String filePath){
    try {
      Scanner scanner = new Scanner(new File(filePath));

      scanner.useDelimiter(",");
      while (scanner.hasNext()){
        System.out.println(scanner.next()+ " ");
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    String filePath = new File("").getAbsolutePath() + File.separator + "data/lesson.csv";
    CSVReader csvObj = new CSVReader();

    csvObj.csvReaderMethod1(filePath);
  }
}
