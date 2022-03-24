import java.util.ArrayList;


/**
 * User class that connects a Nickname with the anonymous Account and moneypools to an aggregate
 */
public class UserAggregate {
    private Username username;
    private Account account;
    private ArrayList<Moneypool> moneypools;

    /**
     * Read user from persistence
     * @param name
     * @param account
     * @param moneypools
     */
    UserAggregate(String name, Account account, ArrayList<Moneypool> moneypools){
        this.username = new Username(name);
        this.account = account;
        this.moneypools = moneypools;
    }

    UserAggregate(String name){
        this.username = new Username(name);
        this.account = new Account();
        this.moneypools = new ArrayList<Moneypool>();
    }

    public Username getUsername() {
        return username;
    }

    public Account getAccount() {
        return account;
    }
}
