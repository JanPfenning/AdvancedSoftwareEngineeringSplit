import java.util.UUID;

public interface UserRepositoryInterface {

    void save(UserAggregate userAggregate) throws PersistExecption;

    UserAggregate getUserFrom(Username username);
    UserAggregate getUserFrom(UUID depotId);

}
