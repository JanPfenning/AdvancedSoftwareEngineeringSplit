import java.util.Objects;

public class Balance {
    private float value;

    Balance(float desiredBalance) throws InvalidBalanceException {
        if(desiredBalance < 0)
            throw new InvalidBalanceException("Balance can't be negative");
        this.value = desiredBalance;
    }

    public float getValue() {
        return value;
    }

    //TODO should be Comperator method
    //https://www.javatpoint.com/Comparator-interface-in-collection-framework
    public boolean isSmallerThan(Balance otherBalance){
        return Float.compare(value, otherBalance.value) < 0;
    }

    @Override
    public String toString() {
        return ""+value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return Float.compare(balance.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
