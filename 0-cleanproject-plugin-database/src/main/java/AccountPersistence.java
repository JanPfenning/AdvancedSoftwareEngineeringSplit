import IO.CSVreader;

import java.util.LinkedList;
import java.util.UUID;

public class AccountPersistence implements AccountPersistenceInterface {
    AccountPersistence(){}

    @Override
    public void save(Account account) {
        //TODO
        System.out.println("saved");
    }

    @Override
    public Account get(String name) {
        System.out.println("requested by name");
        UserPersistenceInterface userMapper = new UserPersistence();
        User u = userMapper.get(name);
        return u != null ? u.getAccount() : null;
    }

    @Override
    public Account get(UUID id) {
        try{
            LinkedList<String> rows = CSVreader.read("mock_Data/Account.csv", "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(id.toString())) {
                    //Balance readBalance = new Balance(Float.parseFloat(rowdata[1]));
                    return new Account(Float.parseFloat(rowdata[1]), rowdata[0]);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("requested by id");
        return null;
    }
}
