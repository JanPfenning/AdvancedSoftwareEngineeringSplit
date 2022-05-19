import java.util.ArrayList;
import java.util.UUID;

public class TransferService {

    private UserRepositoryInterface userRepository;
    private TransferRepositoryInterface transferRepository;

    public TransferService(UserRepositoryInterface userRepository, TransferRepositoryInterface transferRepository) {
        this.userRepository = userRepository;
        this.transferRepository = transferRepository;
    }

    public void sendMoney(String senderDepotId, String receiverDepotId, Amount amount) throws DepotNotFoundException, InvalidBalanceException, TransferOutOfMoneypoolException, PersistExecption {
        UserAggregate sender = userRepository.getUserFrom(UUID.fromString(senderDepotId));
        Depot senderDepot = sender.getDepotBy(UUID.fromString(senderDepotId));

        UserAggregate receiver = userRepository.getUserFrom(UUID.fromString(receiverDepotId));
        Depot receiverDepot = receiver.getDepotBy(UUID.fromString(receiverDepotId));

        if(senderDepot == null) throw new DepotNotFoundException("Sender Depot could not be found");
        if(receiverDepot == null) throw new DepotNotFoundException("Receiver Depot could not be found");

        Balance newSenderBalance = senderDepot.getBalance().subtract(amount);
        Balance newReceiverBalance = receiverDepot.getBalance().add(amount);

        DepotMemento senderDepotMemento = senderDepot.saveToMemento();
        senderDepot.setBalance(newSenderBalance);
        receiverDepot.setBalance(newReceiverBalance);

        Transfer transfer = new Transfer(senderDepot, receiverDepot, amount);
        transferRepository.save(transfer);

        userRepository.save(sender);
        try {
            userRepository.save(receiver);
        }catch(PersistExecption e){
            senderDepot.restoreFromMemento(senderDepotMemento);
            userRepository.save(sender);
        }
    }

    public ArrayList<Transfer> analyseTransferSendings(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        UserAggregate user = userRepository.getUserFrom(uuid);
        Depot depot = user.getDepotBy(uuid);
        return transferRepository.getTransfersWithSender(depot);
    }

    public ArrayList<Transfer> analyseTransferRecievings(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        UserAggregate user = userRepository.getUserFrom(uuid);
        Depot depot = user.getDepotBy(uuid);
        return transferRepository.getTransfersWithRecipient(depot);
    }

}
