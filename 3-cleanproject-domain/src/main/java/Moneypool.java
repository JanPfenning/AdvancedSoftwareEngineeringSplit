import java.util.UUID;

public class Moneypool extends Depot {

    public Moneypool(){
        super();
    }

    public Moneypool(String id, float balance) throws Exception {
        super(UUID.fromString(id), new Balance(balance));
    }

}
