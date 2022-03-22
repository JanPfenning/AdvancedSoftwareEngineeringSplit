import IO.CommandLineReader;

public class InspectUsersAction implements ActionInterface {

    InspectUsersAction(){}

    @Override
    public void act() {
        System.out.println("Which user do you want to inspect?");
        String username = CommandLineReader.readLine();

        UserRepositoryInterface userMapper = new UserRepository();
        UserService userService = new UserService(userMapper);
        User u = userService.getUser(username);

        System.out.println("Corresponding ID:");
        System.out.println(u.getAccount().getId());
        System.out.println("Current Balance:");
        System.out.println(u.getAccount().getBalance());
    }
}
