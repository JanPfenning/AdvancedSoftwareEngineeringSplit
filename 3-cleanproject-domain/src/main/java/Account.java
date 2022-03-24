import java.util.UUID;

public class Account {

    private Balance balance;
    private UUID id;

    // Generate new Account from scratch
    Account(){
        try{
            this.balance = new Balance(0);
            this.id = UUID.randomUUID();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }


    Account(float balance, String uuid) throws Exception {
        this.balance = new Balance(balance);
        this.id = UUID.fromString(uuid);
    }

    public Balance getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

}
