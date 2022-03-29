import java.util.UUID;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Username username){
        super("No user found that is named '"+username+"'");
    }
    public UserNotFoundException(UUID uuid){
        super("No user found thats main Account is '"+uuid+"'");
    }
}
