import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Username {
    private String value;

    Username(String name){
        if(name.length()<2) throw new Error();
        if(name.length()>20) throw new Error();
        for(String badSequence:badSequences){
            if(name.toUpperCase().contains(badSequence)) throw new Error();
        }
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    List<String> badSequences = Arrays.asList(
            "ROOT",
            "ADMIN"
    );

    @Override
    public String toString() {
        return ""+value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return value.equals(username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
