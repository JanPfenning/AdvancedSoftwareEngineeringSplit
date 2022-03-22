import java.util.UUID;

public class Moneypool {
    private UUID id;
    private User owner;
    private float balance;

    public Moneypool(UUID id, User owner, float balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public float getBalance() {
        return balance;
    }
}
