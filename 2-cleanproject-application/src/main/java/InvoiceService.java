import java.util.ArrayList;
import java.util.UUID;

public class InvoiceService {

    private UserRepositoryInterface userPersistence;
    private InvoiceRepositoryInterface invoiceRepository;
    private TransferRepositoryInterface transferRepository;

    public InvoiceService(UserRepositoryInterface userPersistence, InvoiceRepositoryInterface invoiceRepository, TransferRepositoryInterface transferRepository) {
        this.userPersistence = userPersistence;
        this.invoiceRepository = invoiceRepository;
        this.transferRepository = transferRepository;
    }

    public boolean payInvoice(int invoiceId, UUID payerDepotId) throws Exception {
        Invoice invoice = invoiceRepository.get(invoiceId);
        UserAggregate payer = invoice.getRecipient();
        if(invoice.isPaid()) return false;
        TransferService transferService = new TransferService(userPersistence, transferRepository);
        try{
            Depot payerDepot = payer.getDepotBy(payerDepotId);
            if(payerDepot == null)
                return false;
            transferService.sendMoney(payerDepot.getId().toString(), invoice.getBiller().getId().toString(), invoice.getAmount());
            //TODO overwrite invoice as done
        }catch (Exception e){
            return false;
        }
        throw new Exception("Not implemented");
    }

    public ArrayList<Invoice> getInvoicesOf(Username username) throws Exception {
        throw new Exception("Not implemented");
    }

    public ArrayList<Invoice> getInvoicesFromTo(Username username, Depot destinationDepot) throws Exception {
        throw new Exception("Not implemented");
    }

    public void sendInvoice(String billerId, Username recipientUsername, Amount amount){
        UUID biller = UUID.fromString(billerId);
        Depot destinationDepot = userPersistence.getDepotFrom(biller);
        UserAggregate recipient = userPersistence.getUserFrom(recipientUsername);

        Invoice invoice = new Invoice(destinationDepot, recipient, amount);
        invoiceRepository.save(invoice);
    }
}
