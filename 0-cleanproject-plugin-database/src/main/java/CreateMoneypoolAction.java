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
        Username username = null;
        try {
            username = new Username(owner);
            Moneypool pool = userService.createNewMoneypoolFor(username);
            System.out.println("New Moneypool has been registered for "+owner+". The id is: "+pool.getId());
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (PersistExecption e) {
            e.printStackTrace();
        }
    }
}
