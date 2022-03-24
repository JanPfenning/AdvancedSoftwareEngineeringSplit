public class Invoice {

    private int id; //TODO
    private Timestamp creationTimestamp;
    private Depot biller;
    private UserAggregate recipient;
    private Amount amount;

    public Invoice(Depot biller, UserAggregate recipient, Amount amount) {
        this.creationTimestamp = new Timestamp();
        this.biller = biller;
        this.recipient = recipient;
        this.amount = amount;
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
}
