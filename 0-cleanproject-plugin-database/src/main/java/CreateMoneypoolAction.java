import IO.CommandLineReader;

public class CreateMoneypoolAction implements ActionInterface {

    CreateMoneypoolAction(){}

    @Override
    public void act() {
        System.out.println("Who is the owner of the new Moneypool");
        String owner = CommandLineReader.readLine();
        //System.out.println("Please your Password");
        //String desiredPassword = CommandLineReader.readLine();

        UserRepositoryInterface userMapper = new UserRepository();
        UserService userService = new UserService(userMapper);
        Moneypool pool = userService.createNewMoneypoolFor(owner);

        System.out.println("New Moneypool has been registered for "+owner+". The id is: "+pool.getId());
    }
}
