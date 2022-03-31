import java.util.List;
import java.util.UUID;

public interface UserRepositoryInterface {

    void save(UserAggregate userAggregate);
    void save(Account account);
    void save(Moneypool moneypool);
    void save(Moneypool moneypool, UserAggregate owner);

    UserAggregate getUserFrom(Username username);
    UserAggregate getUserFrom(UUID depotId);

    Depot getDepotFrom(UUID depotId);

    Account getAccountFrom(UUID accountId);

    List<Moneypool> getMoneypoolsFrom(Username username);
    Moneypool getMoneypoolFrom(UUID accountId);

}
