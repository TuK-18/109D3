import java.io.Serializable;
import java.net.URL;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapManager implements Serializable {
    public MapManager() {
        tmpMapArray = new ArrayList<String>();
    }

    private ArrayList<String> tmpMapArray;

    // NUMBERS = DENSITY
    // 5 = UNBREAKABLE
    // 0 = DO NOTHING

    public ArrayList<String> loadMapIntoArr(int currLevel) {
        //System.out.println("gggggggggg");
        ArrayList<String>arr = new ArrayList<String>();

        /*URL mapFilePath = MapManager.class.getResource("level"
                + currLevel + ".txt");*/

        File curLevelFile = new File("res/level"
                + currLevel + ".txt");

        //File curLevelFile = new File("level1.txt");

        try (Scanner myScanner = new Scanner(curLevelFile)) {

            while (myScanner.hasNextLine()) {
                //String data = myScanner.nextLine();
                arr.add(myScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find res/level" + currLevel + ".txt");
            e.printStackTrace();
        }

        if(!tmpMapArray.isEmpty()) {
            return tmpMapArray;
        }

        tmpMapArray = arr;
        return arr;
    }

    public void setTmpMapArray(int y, int x) {

        StringBuilder tmpSb = new StringBuilder(tmpMapArray.get(y));
        tmpSb.setCharAt(x,'0');
        //tmpMapArray.set(x,"01111111110");
        tmpMapArray.set(y, tmpSb.toString());
    }

    public boolean emptyMap() {
        return tmpMapArray.isEmpty();
    }

    public ArrayList<String> getTmpMapArray(){
        return tmpMapArray;
    }

}
