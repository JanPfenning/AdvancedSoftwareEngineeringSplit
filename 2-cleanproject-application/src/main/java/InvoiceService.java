import java.util.ArrayList;
import java.util.UUID;

public class InvoiceService {

    private UserRepositoryInterface userRepository;
    private InvoiceRepositoryInterface invoiceRepository;
    private TransferRepositoryInterface transferRepository;

    public InvoiceService(UserRepositoryInterface userRepository, InvoiceRepositoryInterface invoiceRepository, TransferRepositoryInterface transferRepository) {
        this.userRepository = userRepository;
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
        Depot senderDepot = userRepository.getDepotFrom(UUID.fromString(senderDepotId));
        Depot receiverDepot = userRepository.getDepotFrom(invoice.getBiller().getId());
        if(senderDepot == null || receiverDepot == null) throw new Exception("Either of the depots could not be found");

        Balance newSenderBalance = new Balance(senderDepot.getBalance().getValue()-invoice.getAmount().getValue());
        Balance newReceiverBalance = new Balance(receiverDepot.getBalance().getValue()+invoice.getAmount().getValue());

        senderDepot.setBalance(newSenderBalance);
        receiverDepot.setBalance(newReceiverBalance);

        Payment payment = new Payment(invoice, senderDepot);
        transferRepository.save(payment);

        if(senderDepot instanceof Account)
            userRepository.save((Account) senderDepot);
        else
            userRepository.save((Moneypool) senderDepot);
        if(receiverDepot instanceof Account)
            userRepository.save((Account) receiverDepot);
        else
            userRepository.save((Moneypool) receiverDepot);

        return true;
    }

    public ArrayList<Invoice> getInvoicesOf(Username username) {
        return invoiceRepository.get(username);
    }

    public void sendInvoice(String billerId, Username recipientUsername, Amount amount){
        UUID biller = UUID.fromString(billerId);
        Depot destinationDepot = userRepository.getDepotFrom(biller);
        UserAggregate recipient = userRepository.getUserFrom(recipientUsername);

        Invoice invoice = new Invoice(destinationDepot, recipient, amount);
        invoiceRepository.save(invoice);
    }
}
