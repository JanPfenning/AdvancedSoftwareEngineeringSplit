import java.util.List;
import java.util.UUID;

public interface UserRepositoryInterface {

    void save(UserAggregate userAggregate);

    UserAggregate getUserFrom(Username username);
    UserAggregate getUserFrom(UUID depotId);

}
