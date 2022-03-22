public class Invoice {

    private int id; //TODO
    private Timestamp creationTimestamp;
    private User biller;
    private User recipient;
    private Amount amount;

    public Invoice(User biller, User recipient, Amount amount) {
        this.creationTimestamp = new Timestamp();
        this.biller = biller;
        this.recipient = recipient;
        this.amount = amount;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public User getBiller() {
        return biller;
    }

    public User getRecipient() {
        return recipient;
    }

    public Amount getAmount() {
        return amount;
    }
}
