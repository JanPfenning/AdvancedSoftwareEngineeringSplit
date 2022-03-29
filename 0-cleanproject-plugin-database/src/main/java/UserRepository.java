import IO.CSVreader;
import IO.CSVwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class UserRepository implements UserRepositoryInterface {

    public static String USER_FILEPATH = "mock_Data/UsersAccounts.csv";
    public static String ACCOUNT_FILEPATH = "mock_Data/Accounts.csv";
    public static String MONEYPOOL_FILEPATH = "mock_Data/Moneypools.csv";
    public static String USERS_MONEYPOOLS_FILEPATH = "mock_Data/UsersMoneypools.csv";

    public UserRepository() {}

    @Override
    public void save(UserAggregate user) {
        if(this.getUserFrom(user.getUsername()) == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append(user.getUsername()).append(";").append(user.getAccount().getId());
        CSVwriter.writeLine(USER_FILEPATH, sb.toString());
        this.save(user.getAccount());
    }

    @Override
    public void save(Account account) {
        Account oldAccount = this.getAccountFrom(account.getId());
        StringBuilder sb = new StringBuilder();
        sb.append(account.getId()).append(";").append(account.getBalance());
        if (oldAccount != null){
            String row = this.getFirstRowStringFromCSV(oldAccount.getId().toString(), 0, UserRepository.ACCOUNT_FILEPATH);
            try {
                CSVwriter.overwriteLine(ACCOUNT_FILEPATH, row , sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            CSVwriter.writeLine(ACCOUNT_FILEPATH, sb.toString());
        }
    }

    @Override
    public void save(Moneypool newMoneypool) {
        String currentMoneypoolPersistence = getFirstRowStringFromCSV(newMoneypool.getId().toString(), 0, MONEYPOOL_FILEPATH);
        StringBuilder sb = new StringBuilder();
        sb.append(newMoneypool.getId()).append(";").append(newMoneypool.getBalance());
        if(currentMoneypoolPersistence != null){
            try {
                CSVwriter.overwriteLine(MONEYPOOL_FILEPATH, currentMoneypoolPersistence , sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.err.println("Tried to save moneypool that no owner");
        }
    }

    @Override
    public void save(Moneypool newMoneypool, UserAggregate owner) {
        String currentMoneypoolPersistence = getFirstRowStringFromCSV(newMoneypool.getId().toString(), 0, MONEYPOOL_FILEPATH);
        StringBuilder sb = new StringBuilder();
        sb.append(newMoneypool.getId()).append(";").append(newMoneypool.getBalance());
        if(currentMoneypoolPersistence != null){
            System.err.println("tried to save moneypool but this pool already exists");
        }else{
            CSVwriter.writeLine(MONEYPOOL_FILEPATH, sb.toString());
            CSVwriter.writeLine(USERS_MONEYPOOLS_FILEPATH, owner.getUsername()+";"+newMoneypool.getId());
        }
    }

    @Override
    public Account getAccountFrom(Username username){
        UserAggregate u = this.getUserFrom(username);
        if(u==null) return null;
        return u.getAccount();
    }

    @Override
    public Account getAccountFrom(UUID accountId) {
        String row = this.getFirstRowStringFromCSV(accountId.toString(), 0, ACCOUNT_FILEPATH);
        try {
            String[] rowdata = row.split(";");
            return new Account(Float.parseFloat(rowdata[1]), rowdata[0]);
        }catch (NullPointerException ignored){
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Moneypool> getMoneypoolsFrom(Username username) {
        ArrayList<Moneypool> moneypools = new ArrayList<>();
        try{
            LinkedList<String> rows = CSVreader.read(UserRepository.USERS_MONEYPOOLS_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(username.getValue())) {
                    moneypools.add(getMoneypoolFrom(UUID.fromString(rowdata[1])));
                }
            }
            return moneypools;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Moneypool getMoneypoolFrom(UUID moneypoolId) {
        String row = this.getFirstRowStringFromCSV(moneypoolId.toString(), 0, MONEYPOOL_FILEPATH);
        try {
            String[] rowdata = row.split(";");
            return new Moneypool(rowdata[0], Float.parseFloat(rowdata[1]));
        }catch (NullPointerException ignored){
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO remove duplicate code from getUser Functions
    @Override
    public UserAggregate getUserFrom(Username name) {
        String row = getFirstRowStringFromCSV(name.getValue(), 0, UserRepository.USER_FILEPATH);
        if(row == null) return null;
        String[] rowdata = row.split(";");
        UUID uuid = UUID.fromString(rowdata[1]);
        Account account = this.getAccountFrom(uuid);
        ArrayList<Moneypool> moneypools = null;
        try {
            moneypools = this.getMoneypoolsFrom(new Username(rowdata[0]));
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        try {
            return new UserAggregate(rowdata[0], account, moneypools);
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    @Override
    public UserAggregate getUserFrom(UUID id) {
        String row = getFirstRowStringFromCSV(id.toString(), 1, UserRepository.USER_FILEPATH);
        if(row == null) return null;
        String[] rowdata = row.split(";");
        UUID uuid = UUID.fromString(rowdata[1]);
        Account account = this.getAccountFrom(uuid);
        ArrayList<Moneypool> moneypools = null;
        try {
            moneypools = this.getMoneypoolsFrom(new Username(rowdata[0]));
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        try {
            return new UserAggregate(rowdata[0], account, moneypools);
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
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

    private String getFirstRowStringFromCSV(String value, int position, String file){
        try{
            LinkedList<String> rows = CSVreader.read(file, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[position].equals(value)) {
                    return row;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
