import java.util.UUID;

public class Account extends Depot{

    Account(){
        super();
    }

    Account(float balance, String uuid) throws Exception {
        super(UUID.fromString(uuid), new Balance(balance));
    }

}
