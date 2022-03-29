import java.util.Objects;

public class Amount {

    private float value;

    public Amount(float value) throws InvalidAmountException {
        if (value <= 0)
            throw new InvalidAmountException(value+" is negative but Amounts must be positive");
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return ""+value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return Float.compare(amount.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
