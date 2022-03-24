import java.util.UUID;

public class TransferService {

    private UserRepositoryInterface userPersistence;
    private TransferRepositoryInterface transferRepository;

    public TransferService(UserRepositoryInterface userPersistence, TransferRepositoryInterface transferRepository) {
        this.userPersistence = userPersistence;
        this.transferRepository = transferRepository;
    }

    public boolean sendMoney(String senderDepotId, String receiverDepotId, Amount amount) throws Exception {
        Depot senderDepot = userPersistence.getDepotFrom(UUID.fromString(senderDepotId));
        Depot receiverDepot = userPersistence.getDepotFrom(UUID.fromString(receiverDepotId));
        if(senderDepot == null || receiverDepot == null) throw new Exception("Either of the depots could not be found");

        Balance newSenderBalance = new Balance(senderDepot.getBalance().getValue()-amount.getValue());
        Balance newReceiverBalance = new Balance(receiverDepot.getBalance().getValue()+amount.getValue());

        senderDepot.setBalance(newSenderBalance);
        receiverDepot.setBalance(newReceiverBalance);

        Transfer transfer = new Transfer(senderDepot, receiverDepot, amount);
        transferRepository.save(transfer);

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

    public boolean payInvoice(int invoiceId) throws Exception {
        throw new Exception("Not implemented");
    }


}
