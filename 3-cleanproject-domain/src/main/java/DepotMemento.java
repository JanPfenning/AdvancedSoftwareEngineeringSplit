public class DepotMemento {

    private Balance balance;

    public DepotMemento(Balance balance){
        this.balance = balance;
    }

    public Balance getState() {
        return balance;
    }
}
