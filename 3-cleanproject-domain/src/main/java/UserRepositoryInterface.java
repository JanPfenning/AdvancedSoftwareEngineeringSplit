import java.util.UUID;

public interface UserRepositoryInterface {
    void save(User user);
    User get(String username);
    User get(UUID accountOrPoolId);
}
