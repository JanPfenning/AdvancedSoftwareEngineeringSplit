import java.util.ArrayList;
import java.util.UUID;

public class TransferService {

    private UserRepositoryInterface userRepository;
    private TransferRepositoryInterface transferRepository;

    public TransferService(UserRepositoryInterface userRepository, TransferRepositoryInterface transferRepository) {
        this.userRepository = userRepository;
        this.transferRepository = transferRepository;
    }

    public void sendMoney(String senderDepotId, String receiverDepotId, Amount amount) throws DepotNotFoundException, InvalidBalanceException {
        Depot senderDepot = userRepository.getDepotFrom(UUID.fromString(senderDepotId));
        Depot receiverDepot = userRepository.getDepotFrom(UUID.fromString(receiverDepotId));
        if(senderDepot == null) throw new DepotNotFoundException("Sender Depot could not be found");
        if(receiverDepot == null) throw new DepotNotFoundException("Receiver Depot could not be found");

        Balance newSenderBalance = new Balance(senderDepot.getBalance().getValue()-amount.getValue());
        Balance newReceiverBalance = new Balance(receiverDepot.getBalance().getValue()+amount.getValue());

        senderDepot.setBalance(newSenderBalance);
        receiverDepot.setBalance(newReceiverBalance);

        Transfer transfer = new Transfer(senderDepot, receiverDepot, amount);
        transferRepository.save(transfer);

        if(senderDepot instanceof Account)
            userRepository.save((Account) senderDepot);
        else
            userRepository.save((Moneypool) senderDepot);
        if(receiverDepot instanceof Account)
            userRepository.save((Account) receiverDepot);
        else
            userRepository.save((Moneypool) receiverDepot);

    }

    public ArrayList<Transfer> analyseTransferSendings(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        Depot depot = userRepository.getDepotFrom(uuid);
        return transferRepository.getTransfersWithSender(depot);
    }

    public ArrayList<Transfer> analyseTransferRecievings(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        Depot depot = userRepository.getDepotFrom(uuid);
        return transferRepository.getTransfersWithRecipient(depot);
    }

}
