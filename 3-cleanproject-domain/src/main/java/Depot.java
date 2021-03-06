import java.util.UUID;

public class Depot {

    private Balance balance;
    private UUID id;

    public Depot(UUID id, Balance balance){
        this.balance = balance;
        this.id = id;
    }

    public Depot() {
        try{
            this.balance = new Balance(new Money(0));
            this.id = UUID.randomUUID();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public Balance getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public void setBalance(Balance newBalance) throws TransferOutOfMoneypoolException{
        this.balance = newBalance;
    }

    public DepotMemento saveToMemento() {
        System.out.println("Originator: Saving to Memento.");
        return new DepotMemento(balance);
    }

    public void restoreFromMemento(DepotMemento memento) {
        this.balance = memento.getState();
    }
}
