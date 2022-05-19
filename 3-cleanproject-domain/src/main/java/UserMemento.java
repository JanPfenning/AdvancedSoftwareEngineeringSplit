import java.util.ArrayList;

public class UserMemento {
    private Username username;
    private Account account;
    private ArrayList<Moneypool> moneypools;

    UserMemento(Username username, Account account, ArrayList<Moneypool> moneypools) {
        this.username = username;
        this.account = account;
        this.moneypools = moneypools;
    }

    public Username getSavedUsername() {
        return username;
    }

    public Account getSavedAccount() {
        return account;
    }

    public ArrayList<Moneypool> getSavedMoneypools() {
        return moneypools;
    }
}
