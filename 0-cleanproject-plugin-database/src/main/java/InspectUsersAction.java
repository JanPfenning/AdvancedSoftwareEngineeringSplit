import IO.CommandLineReader;
import IO.OutputLogger;

import java.util.UUID;

public class InspectUsersAction implements ActionInterface {

    UserService userService;

    InspectUsersAction() {
        UserRepositoryInterface userRepository = new UserRepository();
        this.userService = new UserService(userRepository);
    }

    @Override
    public void act() {
        OutputLogger.log("Which user do you want to inspect?");
        OutputLogger.log("Enter the main Account-ID or Username?");
        String desiredUser = CommandLineReader.readLine();

        UserAggregate u = null;
        UUID uuid;
        Username username;
        try {
            uuid = UUID.fromString(desiredUser);
        } catch (IllegalArgumentException invalidUUID){
            uuid = null;
        }
        try{
            username = new Username(desiredUser);
        } catch (InvalidUsernameException e) {
            username = null;
        }

        if(username != null){
            try{
                u = userService.getUser(uuid);
            } catch (UserNotFoundException e) {
                u = null;
            }
        }

        if(u != null){
            OutputLogger.log("Corresponding ID:");
            OutputLogger.log(u.getAccount().getId().toString());
            OutputLogger.log("Current Balance:");
            OutputLogger.log(u.getAccount().getBalance().toString());
        }else{
            OutputLogger.log("Account not found");
        }
    }

}
