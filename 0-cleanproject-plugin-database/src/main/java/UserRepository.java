import IO.CSVreader;
import IO.OutputLogger;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.UUID;

public class UserRepository implements UserRepositoryInterface {

    public static String FILEPATH = "mock_Data/UsersAccounts.csv";
    public UserRepository() {}

    @Override
    public void save(User account) {
        //TODO
        OutputLogger.log("Saving was not successfull due to: not implemented");
    }

    @Override
    public User get(String name) {
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(name)) {
                    AccountRepositoryInterface accountMapper = new AccountRepository();
                    Account account = accountMapper.get(UUID.fromString(rowdata[1]));
                    return new User(rowdata[0], account);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
            System.exit(-1);
        }
        return null;
    }

    @Override
    public User get(UUID id) {
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[1].equals(id.toString())) {
                    AccountRepositoryInterface accountMapper = new AccountRepository();
                    Account account = accountMapper.get(UUID.fromString(rowdata[1]));
                    return new User(rowdata[0], account);
                }
            }
        }catch (Exception e){
            System.out.println(e);
            System.exit(-1);
        }
        return null;
    }
}
