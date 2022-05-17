import java.util.Objects;

public final class Money implements Comparable{

    private final float value;

    Money(float value) throws InvalidMoneyException {
        if(value<0)
            throw new InvalidMoneyException("negative Money does not exist");
        this.value = value;
    }

    public Money add(Money m) throws InvalidMoneyException {
        return new Money(this.value + m.value);
    }

    public Money subtract(Money m) throws InvalidMoneyException {
        return new Money(this.value - m.value);
    }

    @Override
    public String toString() {
        return ""+value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Float.compare(money.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Object o) {
        return Float.compare(this.value, ((Money)o).value);
    }
}
