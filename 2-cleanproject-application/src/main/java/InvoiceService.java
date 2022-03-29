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

    public void payInvoice(UUID invoiceId, UUID payerDepotId) throws Exception {
        Invoice invoice = invoiceRepository.get(invoiceId);
        UserAggregate payer = invoice.getRecipient();
        Depot payerDepot = payer.getDepotBy(payerDepotId);
        this.sendMoney(payerDepot.getId().toString(), invoice);
        invoiceRepository.save(invoice);
    }

    private boolean sendMoney(String senderDepotId, Invoice invoice) throws Exception {
        Depot senderDepot = userPersistence.getDepotFrom(UUID.fromString(senderDepotId));
        Depot receiverDepot = userPersistence.getDepotFrom(invoice.getBiller().getId());
        if(senderDepot == null || receiverDepot == null) throw new Exception("Either of the depots could not be found");

        Balance newSenderBalance = new Balance(senderDepot.getBalance().getValue()-invoice.getAmount().getValue());
        Balance newReceiverBalance = new Balance(receiverDepot.getBalance().getValue()+invoice.getAmount().getValue());

        senderDepot.setBalance(newSenderBalance);
        receiverDepot.setBalance(newReceiverBalance);

        Payment payment = new Payment(invoice, senderDepot);
        transferRepository.save(payment);

        if(senderDepot instanceof Account)
            userPersistence.save((Account) senderDepot);
        else
            userPersistence.save((Moneypool) senderDepot);
        if(receiverDepot instanceof Account)
            userPersistence.save((Account) receiverDepot);
        else
            userPersistence.save((Moneypool) receiverDepot);

        return true;
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
