import IO.CSVreader;
import IO.CSVwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class MoneypoolRepository {

    public static String MONEYPOOL_FILEPATH = "mock_Data/Moneypools.csv";
    public static String USERS_MONEYPOOLS_FILEPATH = "mock_Data/UsersMoneypools.csv";

    public MoneypoolRepository() {}

    private void updateMoneypool(Moneypool updatedMoneypool) {
        String currentMoneypoolPersistence = UserRepository.getFirstRowStringFromCSV(updatedMoneypool.getId().toString(), 0, MONEYPOOL_FILEPATH);
        StringBuilder sb = new StringBuilder();
        sb.append(updatedMoneypool.getId()).append(";").append(updatedMoneypool.getBalance());
        if(currentMoneypoolPersistence != null){
            try {
                CSVwriter.overwriteLine(MONEYPOOL_FILEPATH, currentMoneypoolPersistence , sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.err.println("Tried to update Moneypool with id "+updatedMoneypool.getId()+" but this does not exist");
        }
    }

    private void persistNewMoneypool(Moneypool newMoneypool, UserAggregate owner) {
        StringBuilder sb = new StringBuilder();
        sb.append(newMoneypool.getId()).append(";").append(newMoneypool.getBalance());
        CSVwriter.writeLine(MONEYPOOL_FILEPATH, sb.toString());
        CSVwriter.writeLine(USERS_MONEYPOOLS_FILEPATH, owner.getUsername()+";"+newMoneypool.getId());
    }

    public ArrayList<Moneypool> getMoneypoolsFrom(Username username) {
        ArrayList<Moneypool> moneypools = new ArrayList<>();
        try{
            LinkedList<String> rows = CSVreader.read(USERS_MONEYPOOLS_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(username.getValue())) {
                    Moneypool moneypool = getMoneypoolFrom(UUID.fromString(rowdata[1]));
                    if(moneypool != null)
                        moneypools.add(moneypool);
                }
            }
            return moneypools;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public Moneypool getMoneypoolFrom(UUID moneypoolId) {
        String row = UserRepository.getFirstRowStringFromCSV(moneypoolId.toString(), 0, MONEYPOOL_FILEPATH);
        try {
            String[] rowdata = row.split(";");
            return new Moneypool(rowdata[0], Float.parseFloat(rowdata[1]));
        }catch (NullPointerException ignored){
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(ArrayList<Moneypool> moneypools, UserAggregate user) {
        if(moneypools == null) return;
        for(Moneypool moneypool: moneypools){
            Moneypool temp = this.getMoneypoolFrom(moneypool.getId());
            if(temp == null){
                persistNewMoneypool(moneypool, user);
            }else{
                updateMoneypool(moneypool);
            }
        }
    }
}
