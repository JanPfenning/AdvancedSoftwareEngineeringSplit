import IO.CommandLineReader;

import java.util.ArrayList;
import java.util.UUID;

public class ViewInvoiceAction implements ActionInterface {

    InvoiceService service;

    ViewInvoiceAction(){
        UserRepositoryInterface userRepository = new UserRepository();
        InvoiceRepositoryInterface invoiceRepository = new InvoiceRepository();
        TransferRepository transferRepository = new TransferRepository();
        this.service = new InvoiceService(userRepository, invoiceRepository, transferRepository);
    }

    @Override
    public void act() {
        System.out.println("Whats your username?");
        String desiredUser = CommandLineReader.readLine();

        Username username = null;
        try {
            username = new Username(desiredUser);
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }

        if(username != null){
            try {
                ArrayList<Invoice> invoices = this.service.getInvoicesOf(username);
                if(invoices != null){
                    for(Invoice invoice: invoices){
                        System.out.println(invoice.getId()+": "+"send "+invoice.getAmount()+" to "+invoice.getBiller().getId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("No Information about an user is found");
        }
    }
}
