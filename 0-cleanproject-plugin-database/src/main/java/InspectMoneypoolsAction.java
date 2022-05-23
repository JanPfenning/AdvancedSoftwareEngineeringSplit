import IO.CommandLineReader;
import IO.OutputLogger;

import java.util.UUID;

public class InspectMoneypoolsAction implements ActionInterface {

    UserService userService;

    InspectMoneypoolsAction() {
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
            int pagesize = 2;
            MoneypoolIterator moneypools = new MoneypoolIterator(u.getMoneypools(), pagesize);
            while(true){
                moneypools.print();
                if(moneypools.hasNext()){
                    OutputLogger.log("Do you wish to inspect the next "+pagesize+"? y/n");
                    String inp = CommandLineReader.readLine();
                    if(inp.equals("n")){
                        OutputLogger.log("Skipping other moneypools");
                        break;
                    }
                }else{
                    OutputLogger.log("All moneypools shown");
                    break;
                }
            }
        }else{
            OutputLogger.log("Account not found");
        }
    }

}
