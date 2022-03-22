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

}
