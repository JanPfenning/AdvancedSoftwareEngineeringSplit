import IO.CSVreader;
import IO.CSVwriter;

import java.io.FileNotFoundException;
import java.io.IOException;
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
        String oldInvoiceRow = getInvoiceStringFromCsvOf(invoice.getId());

        if(oldInvoiceRow == null){
            sb.append(invoice.getId()).append(delimiter)
                    .append(invoice.getBiller().getId()).append(delimiter)
                    .append(invoice.getRecipient().getUsername()).append(delimiter)
                    .append(invoice.getAmount()).append(delimiter)
                    .append(invoice.getCreationTimestamp().getUnixTime()).append(delimiter)
                    .append(invoice.isPaid()?1:0);
            String serializedInvoice = sb.toString();
            CSVwriter.writeLine(INVOICE_FILEPATH, serializedInvoice);
        }else{
            String[] rowdata = oldInvoiceRow.split(";");
            sb.append(invoice.getId()).append(delimiter)
                    .append(invoice.getBiller().getId()).append(delimiter)
                    .append(invoice.getRecipient().getUsername()).append(delimiter)
                    .append(invoice.getAmount()).append(delimiter)
                    .append(rowdata[4]).append(delimiter)
                    .append(1);
            String serializedInvoice = sb.toString();
            try {
                CSVwriter.overwriteLine(INVOICE_FILEPATH, oldInvoiceRow, serializedInvoice);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    @Override
    public Invoice get(UUID id) {
        String row = getInvoiceStringFromCsvOf(id);
        String[] rowdata = row.split(";");

        UserRepositoryInterface userRepository = new UserRepository();
        try{
            Depot biller = userRepository.getDepotFrom(UUID.fromString(rowdata[1]));
            UserAggregate receiver = userRepository.getUserFrom(new Username(rowdata[2]));
            Amount amount = new Amount(Float.parseFloat(rowdata[3]));
            boolean paid = !rowdata[5].equals("0");
            return new Invoice(UUID.fromString(rowdata[0]), biller, receiver, amount, paid);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public ArrayList<Invoice> get(Username username) {
        return null;
    }

    private String getInvoiceStringFromCsvOf(UUID id){
        try{
            LinkedList<String> rows = CSVreader.read(INVOICE_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[0].equals(id.toString())) {
                    return row;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
