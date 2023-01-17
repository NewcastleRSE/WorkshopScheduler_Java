import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
  public static void main(String[] args) {
    String path = "/Users/rux/Documents/java/files_to_use/lesson.csv";
    BufferedReader reader = null;
    String line = "";

    try{
      reader = new BufferedReader(new FileReader(path));
      while((line = reader.readLine()) !=null){

        String[] row = line.split(",");
        for(String index : row){
          System.out.printf("%-10s",index);
        }
        System.out.println();
      }

    }
    catch(Exception e){
      e.printStackTrace();

    }
    finally {
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}