import java.util.HashMap;
import java.util.UUID;

/**
 * User class that connects a Nickname with the anonymous Account
 */
public class User {
    private Username username;
    private Account account;

    User(String name, Account account){
        this.username = new Username(name);
        this.account = account;
    }

    public Username getUsername() {
        return username;
    }

    public Account getAccount() {
        return account;
    }
}
