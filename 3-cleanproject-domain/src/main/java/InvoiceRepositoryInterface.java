import java.util.ArrayList;
import java.util.UUID;

public interface InvoiceRepositoryInterface {

    void save(Invoice invoice);
    Invoice get(UUID id);
    ArrayList<Invoice> get(Username username);

}
