import IO.CommandLineReader;

public class RequestMoneyAction implements ActionInterface {

    InvoiceService service;

    RequestMoneyAction(){
        UserRepositoryInterface userRepository = new UserRepository();
        InvoiceRepositoryInterface invoiceRepository = new InvoiceRepository();
        TransferRepository transferRepository = new TransferRepository();
        this.service = new InvoiceService(userRepository, invoiceRepository, transferRepository);
    }

    @Override
    public void act() {
        System.out.println("Who is the Person that has to send money?");
        String recipientUserName = CommandLineReader.readLine();
        System.out.println("Which depot should the money be send to?");
        String billerDepot = CommandLineReader.readLine();
        //System.out.println("What is your passwort?");
        //String billerPassword = CommandLineReader.readLine();

        System.out.println("How much money is to be send?");
        String amountString = CommandLineReader.readLine();

        try{
            Amount amount = new Amount(Float.parseFloat(amountString));
            Username username = new Username(recipientUserName);
            service.sendInvoice(billerDepot, username, amount);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }

    }
}
