import java.util.ArrayList;
import java.util.UUID;


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
    UserAggregate(String name, Account account, ArrayList<Moneypool> moneypools) throws InvalidUsernameException {
        this.username = new Username(name);
        this.account = account;
        this.moneypools = moneypools;
    }

    UserAggregate(String name) throws InvalidUsernameException {
        this.username = new Username(name);
        this.account = new Account();
        this.moneypools = new ArrayList<Moneypool>();
    }

    public Username getUsername() {
        return username;
    }

    public Depot getDepotBy(UUID id) {
        UUID accountid = account.getId();
        if(accountid.equals(id)) return account;
        for (Moneypool pool: moneypools) {
            if(pool.getId().equals(id)) return pool;
        }
        return null;
    }

    public Moneypool createMoneypool(){
        Moneypool moneypool = new Moneypool();
        this.moneypools.add(moneypool);
        return moneypool;
    }

    public ArrayList<Moneypool> getMoneypools() {
        return moneypools;
    }

    public Account getAccount() {
        return this.account;
    }

    public UserMemento saveToMemento() {
        System.out.println("Originator: Saving to Memento.");
        return new UserMemento(username, account, moneypools);
    }

    public void restoreFromMemento(UserMemento memento) {
        this.account = memento.getSavedAccount();
        this.moneypools = memento.getSavedMoneypools();
        this.username = memento.getSavedUsername();
    }

}
