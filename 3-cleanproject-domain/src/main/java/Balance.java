public class Balance {
    private float value;

    Balance(float desiredBalance) throws Exception {
        if(desiredBalance < 0)
            throw new Exception("Balance can't be negative");
        this.value = desiredBalance;
    }

    public float getValue() {
        return value;
    }
}
