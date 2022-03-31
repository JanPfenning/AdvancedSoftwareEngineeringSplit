import IO.CSVreader;
import IO.CSVwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class UserRepository implements UserRepositoryInterface {

    public static String USER_FILEPATH = "mock_Data/UsersAccounts.csv";

    private AccountRepository accountRepository = new AccountRepository();
    private MoneypoolRepository moneypoolRepository = new MoneypoolRepository();

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
        accountRepository.save(account);
    }

    @Override
    public void save(Moneypool newMoneypool) {
        moneypoolRepository.save(newMoneypool);
    }

    @Override
    public void save(Moneypool newMoneypool, UserAggregate owner) {
        moneypoolRepository.save(newMoneypool, owner);
    }

    @Override
    public Account getAccountFrom(UUID accountId) {
        return accountRepository.getAccountFrom(accountId);
    }

    @Override
    public ArrayList<Moneypool> getMoneypoolsFrom(Username username) {
        return moneypoolRepository.getMoneypoolsFrom(username);
    }

    @Override
    public Moneypool getMoneypoolFrom(UUID moneypoolId) {
        return moneypoolRepository.getMoneypoolFrom(moneypoolId);
    }

    //TODO remove duplicate code from getUser Functions
    @Override
    public UserAggregate getUserFrom(Username name) {
        String row = UserRepository.getFirstRowStringFromCSV(name.getValue(), 0, UserRepository.USER_FILEPATH);
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
        String row = UserRepository.getFirstRowStringFromCSV(id.toString(), 1, UserRepository.USER_FILEPATH);
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
        Depot x = accountRepository.getAccountFrom(depotId);
        if(x != null) return x;
        x = moneypoolRepository.getMoneypoolFrom(depotId);
        return x;
    }

    public static String getFirstRowStringFromCSV(String value, int position, String file){
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
