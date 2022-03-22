import java.util.UUID;

public interface AccountRepositoryInterface {
    void save(Account account);
    Account get(String ownerName);
    Account get(UUID accountId);
}
