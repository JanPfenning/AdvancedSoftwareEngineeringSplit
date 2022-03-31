import IO.CSVwriter;

import java.io.IOException;
import java.util.UUID;

public class AccountRepository {
    public static String ACCOUNT_FILEPATH = "mock_Data/Accounts.csv";

    public AccountRepository() {}

    public void save(Account account) {
        Account oldAccount = this.getAccountFrom(account.getId());
        StringBuilder sb = new StringBuilder();
        sb.append(account.getId()).append(";").append(account.getBalance());
        if (oldAccount != null){
            String row = UserRepository.getFirstRowStringFromCSV(oldAccount.getId().toString(), 0, ACCOUNT_FILEPATH);
            try {
                CSVwriter.overwriteLine(ACCOUNT_FILEPATH, row , sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            CSVwriter.writeLine(ACCOUNT_FILEPATH, sb.toString());
        }
    }

    public Account getAccountFrom(UUID accountId) {
        String row = UserRepository.getFirstRowStringFromCSV(accountId.toString(), 0, ACCOUNT_FILEPATH);
        try {
            String[] rowdata = row.split(";");
            return new Account(Float.parseFloat(rowdata[1]), rowdata[0]);
        }catch (NullPointerException ignored){
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
