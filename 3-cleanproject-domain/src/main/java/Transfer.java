public class Transfer {

    private int id; //TODO
    private Timestamp sendingTimestamp;
    private User sender;
    private User recipient;
    private Amount amount;

    public Transfer(User sender, User recipient, Amount amount) {
        this.sendingTimestamp = new Timestamp();
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public Timestamp getCreationTimestamp() {
        return sendingTimestamp;
    }

    public User getBiller() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public Amount getAmount() {
        return amount;
    }
}
