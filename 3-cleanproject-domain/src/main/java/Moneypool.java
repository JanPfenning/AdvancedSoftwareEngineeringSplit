import java.util.UUID;

public class Moneypool {
    private UUID id;
    private UserAggregate owner;
    private float balance;

    public Moneypool(UUID id, UserAggregate owner, float balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public UserAggregate getOwner() {
        return owner;
    }

    public float getBalance() {
        return balance;
    }
}
