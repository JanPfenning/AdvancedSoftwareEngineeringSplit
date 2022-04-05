import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MoneypoolTest {

    @Test
    void negativeMoneypool() throws Exception {
        Moneypool moneypool = new Moneypool(UUID.randomUUID().toString(), 10);
        assertThrows(InvalidBalanceException.class, ()->{
            moneypool.setBalance(new Balance(-1));
        });
    }

    @Test
    void emptyMoneypool() throws Exception {
        Moneypool moneypool = new Moneypool(UUID.randomUUID().toString(), 10);
        assertThrows(TransferOutOfMoneypoolException.class, ()->{
            moneypool.setBalance(new Balance(0));
        });
    }

    @Test
    void payFromMoneypool() throws Exception {
        Moneypool moneypool = new Moneypool(UUID.randomUUID().toString(), 10);
        assertThrows(TransferOutOfMoneypoolException.class, ()->{
            moneypool.setBalance(new Balance(2));
        });
    }

    @Test
    void receiveMoney() throws Exception {
        Moneypool moneypool = new Moneypool(UUID.randomUUID().toString(), 10);
        assertDoesNotThrow(()->{
            moneypool.setBalance(new Balance(12));
        });
    }
}