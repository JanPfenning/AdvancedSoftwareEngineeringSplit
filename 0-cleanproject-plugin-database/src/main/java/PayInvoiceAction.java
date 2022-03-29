import IO.CSVreader;
import IO.CommandLineReader;

import java.util.UUID;

public class PayInvoiceAction implements ActionInterface {

    InvoiceService service;

    PayInvoiceAction(){
        UserRepositoryInterface userRepository = new UserRepository();
        InvoiceRepositoryInterface invoiceRepository = new InvoiceRepository();
        TransferRepository transferRepository = new TransferRepository();
        this.service = new InvoiceService(userRepository, invoiceRepository, transferRepository);
    }

    @Override
    public void act() {
        System.out.println("From which account do you wish to pay the invoice?");
        String depotId = CommandLineReader.readLine();
        //System.out.println("What is your Password?");
        //String password = CommandLineReader.readLine();

        System.out.println("Which Invoice should be payed?");
        String invoiceId = CommandLineReader.readLine();

        try{
            this.service.payInvoice(UUID.fromString(invoiceId), UUID.fromString(depotId));
            System.out.println("Invoice has been paied");
        }catch (Exception e){
            System.out.println("Error at payment");
            System.out.println(e);
            System.exit(-1);
        }
    }
}
