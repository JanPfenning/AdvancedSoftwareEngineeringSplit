import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class BalanceTest {

    @Test
    @DisplayName("invalid values for Balance")
    void negativeValues() throws Exception {
        assertThrows(Exception.class, () -> new Balance(-1));
        assertThrows(Exception.class, () -> new Balance(-10));
        assertThrows(Exception.class, () -> new Balance(Integer.MAX_VALUE+1));
    }

    @Test
    @DisplayName("invalid values for Balance")
    void nonNegativeValues() throws Exception {
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
}