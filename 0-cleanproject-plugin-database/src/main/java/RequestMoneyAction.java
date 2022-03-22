import IO.CommandLineReader;

public class RequestMoneyAction implements ActionInterface {

    //TODO
    RequestMoneyAction(){

    }

    @Override
    public void act() {
        System.out.println("Who is the Person that has to send money?");
        CommandLineReader.readLine();
        System.out.println("Who is the Person that is to receive the money?");
        CommandLineReader.readLine();
        System.out.println("How much money is to be send?");
        CommandLineReader.readLine();
        System.out.println("A ows B x");
        System.out.println("is this correct?");
        CommandLineReader.readLine();
    }
}
