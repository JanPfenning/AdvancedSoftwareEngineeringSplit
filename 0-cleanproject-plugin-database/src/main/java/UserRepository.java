import IO.CSVreader;
import IO.CSVwriter;
import IO.OutputLogger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class UserRepository implements UserRepositoryInterface {

    public static String USER_FILEPATH = "mock_Data/UsersAccounts.csv";
    public static String ACCOUNT_FILEPATH = "mock_Data/Accounts.csv";
    public static String MONEYPOOL_FILEPATH = "mock_Data/Moneypools.csv";
    public static String USERS_MONEYPOOLs_FILEPATH = "mock_Data/UsersMoneypools.csv";

    public UserRepository() {}

    @Override
    public void save(UserAggregate user) {
        //TODO
        OutputLogger.log("Saving User was not successfull due to: not implemented");
    }

    @Override
    public void save(Account account) {
        Account oldAccount = this.getAccountFrom(account.getId());
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.ACCOUNT_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(oldAccount.getId().toString())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(account.getId()).append(";").append(account.getBalance());
                    CSVwriter.overwriteLine(ACCOUNT_FILEPATH, row , sb.toString());
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Moneypool moneypool) {
        Moneypool oldMoneyPool = this.getMoneypoolFrom(moneypool.getId());
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.MONEYPOOL_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(oldMoneyPool.getId().toString())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(oldMoneyPool.getId()).append(";").append(moneypool.getBalance());
                    CSVwriter.overwriteLine(MONEYPOOL_FILEPATH, row , sb.toString());
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
        return null;
    }

    @Override
    public ArrayList<Moneypool> getMoneypoolsFrom(Username username) {
        ArrayList<Moneypool> moneypools = new ArrayList<>();
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.USERS_MONEYPOOLs_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(username.getValue())) {
                    moneypools.add(getMoneypoolFrom(UUID.fromString(rowdata[0])));
                }
            }
            return moneypools;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Moneypool getMoneypoolFrom(UUID accountId) {
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.MONEYPOOL_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(accountId.toString())) {
                    //Balance readBalance = new Balance(Float.parseFloat(rowdata[1]));
                    return new Moneypool(rowdata[0], Float.parseFloat(rowdata[1]));
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
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

    @Override
    public Depot getDepotFrom(UUID depotId) {
        Depot x = getAccountFrom(depotId);
        if(x != null) return x;
        x = getMoneypoolFrom(depotId);
        return x;
    }
}
