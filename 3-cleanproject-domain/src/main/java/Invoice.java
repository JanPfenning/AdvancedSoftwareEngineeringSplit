public class Invoice {

    private int id; //TODO
    private Timestamp creationTimestamp;
    private UserAggregate biller;
    private UserAggregate recipient;
    private Amount amount;

    public Invoice(UserAggregate biller, UserAggregate recipient, Amount amount) {
        this.creationTimestamp = new Timestamp();
        this.biller = biller;
        this.recipient = recipient;
        this.amount = amount;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public UserAggregate getBiller() {
        return biller;
    }

    public UserAggregate getRecipient() {
        return recipient;
    }

    public Amount getAmount() {
        return amount;
    }
}
