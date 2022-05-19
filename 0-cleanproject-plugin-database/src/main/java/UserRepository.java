import IO.CSVreader;
import IO.CSVwriter;

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
    public void save(UserAggregate user) throws PersistExecption {
        if(this.getUserFrom(user.getUsername()) == null)
            this.saveNewUser(user);
        else
            this.updateUser(user);
    }

    private void updateUser(UserAggregate user) throws PersistAccountExecption {
        moneypoolRepository.save(user.getMoneypools(), user);
        accountRepository.save(user.getAccount());
    }

    private void saveNewUser(UserAggregate user) throws PersistAccountExecption {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getUsername()).append(";").append(user.getAccount().getId());
        CSVwriter.writeLine(USER_FILEPATH, sb.toString());
        accountRepository.save(user.getAccount());
    }

    @Override
    public UserAggregate getUserFrom(Username name) {
        String row = UserRepository.getFirstRowStringFromCSV(name.toString(), 0, UserRepository.USER_FILEPATH);
        if(row == null) return null;
        return getUserAggregateOfAccountRow(row);
    }

    private UserAggregate getUserAggregateOfAccountRow(String row) {
        String[] rowdata = row.split(";");
        UUID uuid = UUID.fromString(rowdata[1]);
        Account account = accountRepository.getAccountFrom(uuid);
        ArrayList<Moneypool> moneypools = null;
        try {
            moneypools = moneypoolRepository.getMoneypoolsFrom(new Username(rowdata[0]));
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
        String tupleWithAccountId = UserRepository.getFirstRowStringFromCSV(id.toString(), 1, UserRepository.USER_FILEPATH);
        if(tupleWithAccountId == null)
            return getUserFromMoneypoolId(id);
        return getUserAggregateOfAccountRow(tupleWithAccountId);
    }

    private UserAggregate getUserFromMoneypoolId(UUID moneypoolid){
        String row = UserRepository.getFirstRowStringFromCSV(moneypoolid.toString(), 1, UserRepository.USERS_MONEYPOOLS_FILEPATH);
        assert row != null;
        String extractedUsername = extractUsernameOfMoneypoolString(row);
        try {
            Username username = new Username(extractedUsername);
            return getUserFrom(username);
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String extractUsernameOfMoneypoolString(String csvRow) {
        String[] csvData = csvRow.split(";");
        return csvData[0];
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
