package IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVwriter {

    public static void writeLine(String file, String line){
        try(FileWriter pw = new FileWriter(file,true);){
            pw.write(line+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void overwriteLine(String filePath, String oldRecord, String newRecord) throws IOException {
        //Instantiating the Scanner class to read the file
        Scanner sc = new Scanner(new File(filePath));
        //instantiating the StringBuffer class
        StringBuffer buffer = new StringBuffer();
        //Reading lines of the file and appending them to StringBuffer
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();

        //closing the Scanner object
        sc.close();

        //Replacing the old line with new line
        fileContents = fileContents.replaceAll(oldRecord, newRecord);
        //instantiating the FileWriter class
        FileWriter writer = new FileWriter(filePath);

        writer.append(fileContents);
        writer.flush();
    }
}
