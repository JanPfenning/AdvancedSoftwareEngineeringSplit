import IO.CSVreader;
import IO.CSVwriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

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
        String row = getInvoiceStringFromCsvOf(id);
        String[] rowdata = row.split(";");

        UserRepositoryInterface userRepository = new UserRepository();
        try{
            Depot biller = userRepository.getDepotFrom(UUID.fromString(rowdata[1]));
            UserAggregate receiver = userRepository.getUserFrom(new Username(rowdata[2]));
            Amount amount = new Amount(Float.parseFloat(rowdata[3]));
            boolean paid = !rowdata[4].equals("0");
            return new Invoice(Integer.parseInt(rowdata[0]), biller, receiver, amount, paid);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public ArrayList<Invoice> get(Username username) {
        return null;
    }

    private String getInvoiceStringFromCsvOf(int id){
        try{
            LinkedList<String> rows = CSVreader.read(INVOICE_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(Integer.parseInt(rowdata[0]) == id) {
                    return row;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
