import java.util.UUID;

public class InvoiceService {

    private UserRepositoryInterface userPersistence;
    private InvoiceRepositoryInterface invoiceRepository;

    public InvoiceService(UserRepositoryInterface userPersistence, InvoiceRepositoryInterface invoiceRepository) {
        this.userPersistence = userPersistence;
        this.invoiceRepository = invoiceRepository;
    }

    public boolean payInvoice(int invoiceId) throws Exception {
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
