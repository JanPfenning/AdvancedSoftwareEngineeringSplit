import java.util.List;
import java.util.UUID;

public interface TransferRepositoryInterface {

    void save(Transfer transfer);
    Transfer get(int id);

}
