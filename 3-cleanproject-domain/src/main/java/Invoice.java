import java.util.UUID;

public class Invoice {

    private UUID id;
    private Timestamp creationTimestamp;
    private Depot biller;
    private UserAggregate recipient;
    private Amount amount;
    private boolean paid;

    public Invoice(Depot biller, UserAggregate recipient, Amount amount) {
        this.id = UUID.randomUUID();
        this.creationTimestamp = new Timestamp();
        this.biller = biller;
        this.recipient = recipient;
        this.amount = amount;
        this.paid = false;
    }

    public Invoice(UUID id, Depot biller, UserAggregate recipient, Amount amount, boolean paid) {
        this.id = id;
        this.biller = biller;
        this.recipient = recipient;
        this.amount = amount;
        this.paid = paid;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public Depot getBiller() {
        return biller;
    }

    public UserAggregate getRecipient() {
        return recipient;
    }

    public Amount getAmount() {
        return amount;
    }

    public UUID getId(){
        return this.id;
    }

    public boolean isPaid(){
        return this.paid;
    }
}
