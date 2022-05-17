import java.time.Instant;
import java.util.Objects;

public final class Timestamp {
    private final long unixTime;

    public Timestamp(){
        this.unixTime = Instant.now().getEpochSecond();
    }

    public long getUnixTime() {
        return unixTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp timestamp = (Timestamp) o;
        return unixTime == timestamp.unixTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unixTime);
    }

}
