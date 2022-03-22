import IO.CSVreader;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class TransaktionsManagerTest {

    @Test
    void read() {
        try{
            LinkedList<String> rows = CSVreader.read("C:\\Users\\i518078\\IntellijProjects\\AdvancedSoftwareEngingeering_Programmentwurf\\Programmentwurf_Code\\mock_Data\\Blockchain\\0_GenesisBlock.csv", ";");
            for(String row: rows){
                System.out.println(row);
            }
            //Transaction t = Transaction.deserialize(rows);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void write() {
    }
}