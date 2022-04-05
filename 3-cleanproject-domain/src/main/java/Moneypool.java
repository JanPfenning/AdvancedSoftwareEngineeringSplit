import java.util.UUID;

public class Moneypool extends Depot {

    public Moneypool(){
        super();
    }

    public Moneypool(String id, float balance) throws Exception {
        super(UUID.fromString(id), new Balance(balance));
    }

    @Override
    public void setBalance(Balance newBalance) throws TransferOutOfMoneypoolException {
        if(this.getBalance().isSmallerThan(newBalance)){
            throw new TransferOutOfMoneypoolException("Attempt to transfer money out of money pool "+getId());
        }
    }
}
