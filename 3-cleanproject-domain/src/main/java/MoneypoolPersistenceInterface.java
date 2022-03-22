import java.util.UUID;

public interface MoneypoolPersistenceInterface {
    void save(Moneypool moneypool);
    Moneypool get(String ownerName);
    Moneypool get(UUID moneyPoolId);
}
