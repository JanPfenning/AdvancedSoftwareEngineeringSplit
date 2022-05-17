import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceTest {

    @Test
    @DisplayName("invalid values for Balance")
    public void negativeValues() throws Exception {
        assertThrows(Exception.class, () -> new Balance(new Money(-1)));
        assertThrows(Exception.class, () -> new Balance(new Money(-10)));
        assertThrows(Exception.class, () -> new Balance(new Money(Integer.MAX_VALUE+1)));
    }

    @Test
    @DisplayName("valid values for Balance")
    public void nonNegativeValues() throws Exception {
        assertDoesNotThrow(() -> {
            new Balance(new Money(0));
        });
        assertDoesNotThrow(() -> {
            new Balance(new Money(1));
        });
        assertDoesNotThrow(() -> {
            new Balance(new Money(10));
        });
        assertDoesNotThrow(() -> {
            new Balance(new Money(Integer.MAX_VALUE));
        });
    }

    @Test
    @DisplayName("Getter test")
    public void getter(){
        assertDoesNotThrow(() -> {
            Balance b = new Balance(new Money(0));
            assertEquals(new Money(0), b.getValue());
        });
    }
}