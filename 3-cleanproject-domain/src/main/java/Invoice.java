public class Invoice {

    private int id;
    private Timestamp creationTimestamp;
    private Depot biller;
    private UserAggregate recipient;
    private Amount amount;
    private boolean paid;

    public Invoice(Depot biller, UserAggregate recipient, Amount amount) {
        this.id = 0; // TODO increment an atomic integer or so
        this.creationTimestamp = new Timestamp();
        this.biller = biller;
        this.recipient = recipient;
        this.amount = amount;
        this.paid = false;
    }

    public Invoice(int id, Depot biller, UserAggregate recipient, Amount amount, boolean paid) {
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

    public int getId(){
        return this.id;
    }

    public boolean isPaid(){
        return this.paid;
    }
}
