import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAggregateTest {

    @Test
    void updateAccount() throws Exception {
        UserAggregate user = new UserAggregate("Jan");
        assertEquals(new Balance(0), user.getAccount().getBalance());
        user.getAccount().setBalance(new Balance(1));
        assertEquals(new Balance(1), user.getAccount().getBalance());
    }

}