import IO.CSVreader;
import IO.OutputLogger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class UserRepository implements UserRepositoryInterface {

    public static String USER_FILEPATH = "mock_Data/UsersAccounts.csv";
    public static String ACCOUNT_FILEPATH = "mock_Data/Accounts.csv";
    public static String MONEYPOOL_FILEPATH = "mock_Data/UsersMoneypools.csv";

    public UserRepository() {}

    @Override
    public void save(UserAggregate account) {
        //TODO
        OutputLogger.log("Saving was not successfull due to: not implemented");
    }

    @Override
    public void save(Account account) {
        OutputLogger.log("Saving was not successfull due to: not implemented");
    }

    @Override
    public void save(Moneypool moneypool) {
        OutputLogger.log("Saving was not successfull due to: not implemented");
    }


    @Override
    public Account getAccountFrom(Username username) throws Exception{
        UserAggregate u = this.getUserFrom(username);
        if(u == null){
            throw new Exception("Username does not exist. Maybe the User deleted the connection from Username to the Account");
        }
        return u.getAccount();
    }

    @Override
    public Account getAccountFrom(UUID accountId) {
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.ACCOUNT_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(accountId.toString())) {
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

    @Override
    public ArrayList<Moneypool> getMoneypoolsFrom(Username username) {
        OutputLogger.log("Saving was not successfull due to: not implemented");
        return null;
    }

    @Override
    public Moneypool getMoneypoolFrom(UUID accountId) {
        OutputLogger.log("Saving was not successfull due to: not implemented");
        return null;
    }

    @Override
    public UserAggregate getUserFrom(Username name) {
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.USER_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                Username test = new Username(rowdata[0]);
                if(name.equals(test)) {
                    UUID uuid = UUID.fromString(rowdata[1]);
                    Account account = this.getAccountFrom(uuid);
                    ArrayList<Moneypool> moneypools = this.getMoneypoolsFrom(name);
                    return new UserAggregate(rowdata[0], account, moneypools);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
            System.exit(-1);
        }
        return null;
    }

    @Override
    public UserAggregate getUserFrom(UUID id) {
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.USER_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[1].equals(id.toString())) {
                    UUID uuid = UUID.fromString(rowdata[1]);
                    Account account = this.getAccountFrom(uuid);
                    ArrayList<Moneypool> moneypools = this.getMoneypoolsFrom(new Username(rowdata[0]));
                    return new UserAggregate(rowdata[0], account, moneypools);
                }
            }
        }catch (Exception e){
            System.out.println(e);
            System.exit(-1);
        }
        return null;
    }
}
