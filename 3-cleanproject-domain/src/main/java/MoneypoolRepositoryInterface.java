import java.util.UUID;

public interface MoneypoolRepositoryInterface {
    void save(Moneypool moneypool);
    Moneypool get(String ownerName);
    Moneypool get(UUID moneyPoolId);
}
