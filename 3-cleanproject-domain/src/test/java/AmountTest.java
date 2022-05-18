import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AmountTest {

    @Test
    @DisplayName("invalid values for Amount")
    public void negativeValues() throws Exception {
        assertThrows(Exception.class, () -> new Amount(new Money(0)));
        assertThrows(Exception.class, () -> new Amount(new Money(-1)));
        assertThrows(Exception.class, () -> new Amount(new Money(-10)));
        assertThrows(Exception.class, () -> new Amount(new Money(Integer.MAX_VALUE+1)));
    }

    @Test
    @DisplayName("valid values for Amount")
    public void nonNegativeValues() throws Exception {
        assertDoesNotThrow(() -> {
            new Amount(new Money((float)0.1));
        });
        assertDoesNotThrow(() -> {
            new Amount(new Money(1));
        });
        assertDoesNotThrow(() -> {
            new Amount(new Money(10));
        });
        assertDoesNotThrow(() -> {
            new Amount(new Money(Integer.MAX_VALUE));
        });
    }

    @Test
    @DisplayName("Getter test")
    public void getter(){
        assertDoesNotThrow(() -> {
            Amount a = new Amount(new Money(10));
            assertEquals(new Money(10), a.getValue());
        });
    }
}