import java.util.UUID;

public class Transfer {

    private int id; //TODO initialize id with atomic int
    private Timestamp sendingTimestamp;
    private Depot sender;
    private Depot receiver;
    private Amount amount;

    public Transfer(Depot sender, Depot recipient, Amount amount) {
        this.sendingTimestamp = new Timestamp();
        this.sender = sender;
        this.receiver = recipient;
        this.amount = amount;
    }

    public Timestamp getCreationTimestamp() {
        return sendingTimestamp;
    }

    public Depot getSender() {
        return sender;
    }

    public Depot getRecipient() {
        return receiver;
    }

    public Amount getAmount() {
        return amount;
    }

    public int getId(){
        return id;
    }
}
