import IO.CSVreader;

import java.util.LinkedList;
import java.util.UUID;

public class UserPersistence implements UserPersistenceInterface {

    public UserPersistence() {}

    @Override
    public void save(User account) {
        //TODO
        System.out.println("saved");
    }

    @Override
    public User get(String name) {
        try{
            LinkedList<String> rows = CSVreader.read("mock_Data/Users.csv", "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(name)) {
                    AccountPersistenceInterface accountMapper = new AccountPersistence();
                    Account account = accountMapper.get(UUID.fromString(rowdata[1]));
                    return new User(rowdata[0], account);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public User get(UUID id) {
        try{
            LinkedList<String> rows = CSVreader.read("mock_Data/Users.csv", "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[1].equals(id.toString())) {
                    AccountPersistenceInterface accountMapper = new AccountPersistence();
                    Account account = accountMapper.get(UUID.fromString(rowdata[1]));
                    return new User(rowdata[0], account);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
