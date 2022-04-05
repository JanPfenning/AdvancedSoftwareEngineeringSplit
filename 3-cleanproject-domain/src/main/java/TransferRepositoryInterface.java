import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TransferRepositoryInterface {

    void save(Transfer transfer);
    Transfer get(UUID id);
    ArrayList<Transfer> getTransfersWithSender(Depot id);
    ArrayList<Transfer> getTransfersWithRecipient(Depot id);

}
