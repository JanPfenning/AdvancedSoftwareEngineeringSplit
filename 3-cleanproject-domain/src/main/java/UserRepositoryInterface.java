import java.util.List;
import java.util.UUID;

public interface UserRepositoryInterface {

    void save(UserAggregate userAggregate);
    void save(Account account);
    void save(Moneypool moneypool);

    UserAggregate getUserFrom(Username username);
    UserAggregate getUserFrom(UUID accountOrPoolId);

    Account getAccountFrom(Username username) throws Exception;
    Account getAccountFrom(UUID accountId);

    List<Moneypool> getMoneypoolsFrom(Username username);
    Moneypool getMoneypoolFrom(UUID accountId);

}
