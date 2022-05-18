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
        UUID uuid = null;
        Username username = null;
        try {
            uuid = UUID.fromString(desiredUser);
        } catch (IllegalArgumentException invalidUUID){
            try{
                username = new Username(desiredUser);
            } catch (InvalidUsernameException e) {}
        }

        u = null;
        if(username == null){
            try{
                u = userService.getUser(uuid);
            } catch (UserNotFoundException e) {}
        }else{
            try {
                u = userService.getUser(username);
            } catch (UserNotFoundException e) {}
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
