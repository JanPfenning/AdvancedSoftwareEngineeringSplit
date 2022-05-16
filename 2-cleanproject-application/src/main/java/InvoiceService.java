import java.util.ArrayList;
import java.util.UUID;

//TODO think of the idea, to pay Invoices with open invoices from others. Skip the middle person.
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
        if(payerDepot == null) throw new DepotNotFoundException("Depot with id "+payerDepotId+" not found for recipient "+payer.getUsername());
        this.sendMoney(payerDepot.getId().toString(), invoice);
        invoiceRepository.save(invoice);
    }

    private void sendMoney(String senderDepotId, Invoice invoice) throws Exception {
        UserAggregate sender = userRepository.getUserFrom(UUID.fromString(senderDepotId));
        Depot senderDepot = sender.getDepotBy(UUID.fromString(senderDepotId));
        UserAggregate receiver = userRepository.getUserFrom(invoice.getBiller().getId());
        Depot receiverDepot = receiver.getDepotBy(invoice.getBiller().getId());
        if(senderDepot == null || receiverDepot == null) throw new Exception("Either of the depots could not be found");

        Balance newSenderBalance = new Balance(senderDepot.getBalance().getValue()-invoice.getAmount().getValue());
        Balance newReceiverBalance = new Balance(receiverDepot.getBalance().getValue()+invoice.getAmount().getValue());

        senderDepot.setBalance(newSenderBalance);
        receiverDepot.setBalance(newReceiverBalance);

        Payment payment = new Payment(invoice, senderDepot);
        transferRepository.save(payment);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    public ArrayList<Invoice> getInvoicesOf(Username username) {
        return invoiceRepository.get(username);
    }

    public void sendInvoice(String billerString, Username recipientUsername, Amount amount) throws UnknownUserAggregateException {
        UUID billerId = UUID.fromString(billerString);
        UserAggregate biller = userRepository.getUserFrom(billerId);
        Depot destinationDepot = biller.getDepotBy(billerId);
        UserAggregate recipient = userRepository.getUserFrom(recipientUsername);

        if(recipient == null)
            throw new UnknownUserAggregateException("User with Username "+recipientUsername+" Does not exist");

        Invoice invoice = new Invoice(destinationDepot, recipient, amount);
        invoiceRepository.save(invoice);
    }
}
