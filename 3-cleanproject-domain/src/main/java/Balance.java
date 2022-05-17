import java.util.Objects;

public final class Balance implements Comparable{

    private final Money value;

    Balance(Money money) throws InvalidBalanceException {
        try{
            if(money.compareTo(new Money(0))<0)
                throw new InvalidBalanceException("Balance can't be negative");
        } catch (InvalidMoneyException e){
            System.exit(-1);
        }
        this.value = money;
    }

    public Balance add(Amount a) throws InvalidBalanceException {
        try {
            return new Balance(this.value.add(a.getValue()));
        }catch (InvalidMoneyException e){
            throw new InvalidBalanceException("Balance can't be negative");
        }    }

    public Balance subtract(Amount a) throws InvalidBalanceException {
        try {
            return new Balance(this.value.subtract(a.getValue()));
        }catch (InvalidMoneyException e){
            throw new InvalidBalanceException("Balance can't be negative");
        }
    }

    public Money getValue(){
        return this.value;
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
        return balance.value.compareTo(value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Object o) {
        return this.value.compareTo(((Balance)o).value);
    }
}
