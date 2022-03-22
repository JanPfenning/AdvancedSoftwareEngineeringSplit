public class Amount {
    private float value;

    public Amount(float value) throws Exception {
        if (value <= 0)
            throw new Exception("Values must be positive");
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
