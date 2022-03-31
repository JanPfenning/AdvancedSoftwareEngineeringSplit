import IO.CSVreader;
import IO.CSVwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class UserRepository implements UserRepositoryInterface {

    public static String USER_FILEPATH = "mock_Data/UsersAccounts.csv";
    public static String USERS_MONEYPOOLS_FILEPATH = "mock_Data/UsersMoneypools.csv";

    private AccountRepository accountRepository = new AccountRepository();
    private MoneypoolRepository moneypoolRepository = new MoneypoolRepository();

    public UserRepository() {}

    @Override
    public void save(UserAggregate user) {
        if(this.getUserFrom(user.getUsername()) == null)
            this.saveNewUser(user);
        else
            this.updateUser(user);
    }

    //TODO test
    private void updateUser(UserAggregate user){
        moneypoolRepository.save(user.getMoneypools(), user);
        accountRepository.save(user.getAccount());
    }

    //TODO test
    private void saveNewUser(UserAggregate user){
        StringBuilder sb = new StringBuilder();
        sb.append(user.getUsername()).append(";").append(user.getAccount().getId());
        CSVwriter.writeLine(USER_FILEPATH, sb.toString());
        accountRepository.save(user.getAccount());
    }

    private Account getAccountFrom(UUID accountId) {
        return accountRepository.getAccountFrom(accountId);
    }

    private ArrayList<Moneypool> getMoneypoolsFrom(Username username) {
        return moneypoolRepository.getMoneypoolsFrom(username);
    }

    private Moneypool getMoneypoolFrom(UUID moneypoolId) {
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
        //TODO if no account has been found, search for MoneypoolIds the User owns
        String row = UserRepository.getFirstRowStringFromCSV(id.toString(), 1, UserRepository.USER_FILEPATH);
        if(row == null){
            // No Account id was provided but maybe a Moneypoolid
            row = UserRepository.getFirstRowStringFromCSV(id.toString(), 1, UserRepository.USERS_MONEYPOOLS_FILEPATH);
            String[] rowdata = row.split(";");
            //Get name of Moneypool row
            String username = rowdata[0];
            try {
                return getUserFrom(new Username(username));            //Get Useraggregate of account
            } catch (InvalidUsernameException e) {
                e.printStackTrace();
            }
        }
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
