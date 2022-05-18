import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAggregateTest {

    @Test
    void updateAccount() throws Exception {
        UserAggregate user = new UserAggregate("Jan");
        assertEquals(new Balance(new Money(0)), user.getAccount().getBalance());
        user.getAccount().setBalance(new Balance(new Money(1)));
        assertEquals(new Balance(new Money(1)), user.getAccount().getBalance());
    }

}