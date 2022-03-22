import java.util.UUID;

public interface UserPersistenceInterface {
    void save(User user);
    User get(String username);
    User get(UUID accountOrPoolId);
}
