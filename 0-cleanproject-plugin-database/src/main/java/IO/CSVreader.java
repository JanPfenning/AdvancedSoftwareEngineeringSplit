package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class CSVreader {
    public static LinkedList<String> read(String path, String delimiter) throws FileNotFoundException {
        LinkedList<String> csvData = new LinkedList<String>();
        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(delimiter);
        while (sc.hasNext()) {
            csvData.add(sc.next());
        }
        sc.close();
        return csvData;
    }
}
