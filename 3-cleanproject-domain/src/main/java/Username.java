import java.util.Arrays;
import java.util.List;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Username))
            return false;
        Username other = (Username)o;
        return other.value.equals(this.value);
    }
}
