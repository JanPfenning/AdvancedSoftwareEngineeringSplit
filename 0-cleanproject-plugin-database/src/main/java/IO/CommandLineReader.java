package IO;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLineReader {
    public static String readLine(){
        String input;
        try{
            BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
            input = buffer.readLine();
            return input;
        }catch (Exception e){
            System.out.println("No console available");
            System.exit(-1);
        }
        return null;
    }
}
