public interface InvoiceRepositoryInterface {

    void save(Invoice invoice);
    Invoice get(int id);

}
