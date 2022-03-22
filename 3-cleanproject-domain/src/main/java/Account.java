import java.util.UUID;

public class Account {

    private Balance balance;
    private UUID id;

    // Generate new Account from scratch
    Account() throws Exception {
        this.balance = new Balance(0);
        this.id = UUID.randomUUID();
    }


    Account(float balance, String uuid) throws Exception {
        this.balance = new Balance(balance);
        this.id = UUID.fromString(uuid);
    }

    public Balance getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

}
