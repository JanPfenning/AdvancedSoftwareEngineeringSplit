public class Transfer {

    private int id; //TODO
    private Timestamp sendingTimestamp;
    private UserAggregate sender;
    private UserAggregate recipient;
    private Amount amount;

    public Transfer(UserAggregate sender, UserAggregate recipient, Amount amount) {
        this.sendingTimestamp = new Timestamp();
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public Timestamp getCreationTimestamp() {
        return sendingTimestamp;
    }

    public UserAggregate getBiller() {
        return sender;
    }

    public UserAggregate getRecipient() {
        return recipient;
    }

    public Amount getAmount() {
        return amount;
    }
}
