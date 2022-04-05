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
            this.balance = new Balance(0);
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
}
