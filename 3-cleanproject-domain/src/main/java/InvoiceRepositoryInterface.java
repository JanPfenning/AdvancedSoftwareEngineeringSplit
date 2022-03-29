import java.util.ArrayList;

public interface InvoiceRepositoryInterface {

    void save(Invoice invoice);
    Invoice get(int id);
    ArrayList<Invoice> get(Username username);

}
