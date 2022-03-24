import IO.CommandLineReader;

public class CreateAccountAction implements ActionInterface {

    //TODO
    CreateAccountAction(){}

    @Override
    public void act() {
        System.out.println("Please enter a new Nickname");
        String desiredUsername = CommandLineReader.readLine();
        //System.out.println("Please enter a Password");
        //String desiredPassword = CommandLineReader.readLine();

        UserRepositoryInterface userMapper = new UserRepository();
        UserService userService = new UserService(userMapper);
        UserAggregate u = userService.createNewUser(desiredUsername);

        System.out.println("New user has been registered. Your main account has the id: "+u.getAccount().getId());
    }
}
