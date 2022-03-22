import java.time.Instant;

public class Timestamp {
    private long unixTime;

    public Timestamp(){
        this.unixTime = Instant.now().getEpochSecond();
    }

    public long getUnixTime() {
        return unixTime;
    }
}
