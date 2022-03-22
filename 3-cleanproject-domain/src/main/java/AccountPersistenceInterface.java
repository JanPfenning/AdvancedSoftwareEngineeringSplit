import java.util.UUID;

public interface AccountPersistenceInterface {
    void save(Account account);
    Account get(String ownerName);
    Account get(UUID accountId);
}
