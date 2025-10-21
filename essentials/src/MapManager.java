import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapManager extends ResourceManager{
    public MapManager() {
        super();
    }

    // NUMBERS = DENSITY
    // 5 = UNBREAKABLE
    // 0 = DO NOTHING

    public ArrayList<String> loadMapIntoArr(int currLevel) {
        //System.out.println("gggggggggg");
        ArrayList<String>arr = new ArrayList<String>();

        File curLevelFile = new File("res/level"
                + currLevel + ".txt");
        //File curLevelFile = new File("level1.txt");

        try (Scanner myScanner = new Scanner(curLevelFile)) {

            while (myScanner.hasNextLine()) {
                //String data = myScanner.nextLine();
                arr.add(myScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find level" + currLevel + ".txt");
            e.printStackTrace();
        }

        return arr;
    }
}
