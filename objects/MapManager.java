import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapManager extends ResourceManager{
    public MapManager() {
        super();
    }

    public ArrayList<String> loadMapIntoArr(int currLevel) {
        ArrayList<String>arr = new ArrayList<String>();

        File curLevelFile = new File("level"
                + currLevel + ".txt");

        try (Scanner myScanner = new Scanner(curLevelFile)) {

            while (myScanner.hasNextLine()) {
                //String data = myScanner.nextLine();
                arr.add(myScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find level" + currLevel + " file");
            e.printStackTrace();
        }

        return arr;
    }
}
