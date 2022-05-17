import java.util.Objects;

public final class Amount implements Comparable{

    private final Money value;

    public Amount(Money money) throws InvalidAmountException {
        try{
            if(money.compareTo(new Money(0))<=0)
                throw new InvalidAmountException("Amount must be positive");
        } catch (InvalidMoneyException e){
            System.exit(-1);
        }
        this.value = money;
    }

    public Money getValue(){
        return this.value;
    }

    public Amount add(Money m) throws InvalidAmountException {
        try{
            return new Amount(m.add(this.value));
        } catch (InvalidMoneyException e){
            throw new InvalidAmountException("Amount must be positive");
        }
    }

    public Amount subtract(Money m) throws InvalidAmountException {
        try{
            return new Amount(m.subtract(this.value));
        } catch (InvalidMoneyException e){
            throw new InvalidAmountException("Amount must be positive");
        }
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
        return Objects.equals(value, amount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Object o) {
        return this.value.compareTo(o);
    }
}
