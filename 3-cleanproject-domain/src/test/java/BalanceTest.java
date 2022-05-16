import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceTest {

    @Test
    @DisplayName("invalid values for Balance")
    public void negativeValues() throws Exception {
        assertThrows(Exception.class, () -> new Balance(-1));
        assertThrows(Exception.class, () -> new Balance(-10));
        assertThrows(Exception.class, () -> new Balance(Integer.MAX_VALUE+1));
    }

    @Test
    @DisplayName("valid values for Balance")
    public void nonNegativeValues() throws Exception {
        assertDoesNotThrow(() -> {
            new Balance(0);
        });
        assertDoesNotThrow(() -> {
            new Balance(1);
        });
        assertDoesNotThrow(() -> {
            new Balance(10);
        });
        assertDoesNotThrow(() -> {
            new Balance(Integer.MAX_VALUE);
        });
    }

    @Test
    @DisplayName("Getter test")
    public void getter(){
        assertDoesNotThrow(() -> {
            Balance b = new Balance(0);
            assertEquals(0, b.getValue());
        });
    }
}