import IO.CommandLineReader;

public class InspectUsersAction implements ActionInterface {

    InspectUsersAction(){}

    @Override
    public void act() {
        System.out.println("Which user do you want to inspect?");
        String username = CommandLineReader.readLine();

        UserPersistenceInterface userMapper = new UserPersistence();
        UserManager userManager = new UserManager(userMapper);
        User u = userManager.getUser(username);

        System.out.println("Corresponding ID:");
        System.out.println(u.getAccount().getId());
        System.out.println("Current Balance:");
        System.out.println(u.getAccount().getBalance());
    }
}
