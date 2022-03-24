import IO.CommandLineReader;
import IO.OutputLogger;

public class InspectUsersAction implements ActionInterface {

    InspectUsersAction(){}

    @Override
    public void act() {
        OutputLogger.log("Which user do you want to inspect?");
        String username = CommandLineReader.readLine();

        UserRepositoryInterface userMapper = new UserRepository();
        UserService userService = new UserService(userMapper);
        User u = userService.getUser(username);

        OutputLogger.log("Corresponding ID:");
        OutputLogger.log(u.getAccount().getId().toString());
        OutputLogger.log("Current Balance:");
        OutputLogger.log(u.getAccount().getBalance().toString());
    }
}
