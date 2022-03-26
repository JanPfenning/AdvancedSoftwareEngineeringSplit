import IO.CSVwriter;

public class InvoiceRepository implements InvoiceRepositoryInterface{

    public static String INVOICE_FILEPATH = "mock_Data/invoices.csv";

    InvoiceRepository(){}

    @Override
    public void save(Invoice invoice) {
        String delimiter = ";";
        StringBuilder sb = new StringBuilder();

        sb.append(invoice.getId()).append(delimiter)
                .append(invoice.getBiller().getId()).append(delimiter)
                .append(invoice.getRecipient().getUsername()).append(delimiter)
                .append(invoice.getAmount()).append(delimiter)
                .append(invoice.getCreationTimestamp().getUnixTime()).append(delimiter)
                .append(0);
        CSVwriter.writeLine(INVOICE_FILEPATH, sb.toString());
    }

    @Override
    public Invoice get(int id) {
        return null;
    }
}
