import IO.CSVreader;
import IO.CSVwriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class TransferRepository implements TransferRepositoryInterface{

    public static String TRANSFER_FILEPATH = "mock_Data/Transfers.csv";

    TransferRepository(){}

    @Override
    public void save(Transfer transfer) {
        String delimiter = ";";
        StringBuilder sb = new StringBuilder();

        String invoiceid = transfer instanceof Payment ? ""+((Payment) transfer).getInvoice().getId() : "";
        sb.append(transfer.getId()).append(delimiter)
                .append(transfer.getSender().getId()).append(delimiter)
                .append(transfer.getRecipient().getId()).append(delimiter)
                .append(transfer.getAmount()).append(delimiter)
                .append(invoiceid).append(delimiter)
                .append(transfer.getCreationTimestamp().getUnixTime());
        CSVwriter.writeLine(TRANSFER_FILEPATH, sb.toString());
    }

    @Override
    public Transfer get(int id) {
        return null;
    }

    @Override
    public ArrayList<Transfer> getTransfersWithSender(Depot depot) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        try{
            LinkedList<String> rows = CSVreader.read(TRANSFER_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[1].equals(depot.getId().toString())) {
                    UUID readUUID = UUID.fromString(rowdata[0]);
                    Depot sender = new Depot(UUID.fromString(rowdata[1]), new Balance(0));
                    Depot receiver = new Depot(UUID.fromString(rowdata[2]), new Balance(0));
                    Amount amount = new Amount(Float.parseFloat(rowdata[3]));
                    transfers.add(new Transfer(readUUID, sender, receiver, amount));
                }
            }
            return transfers;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ArrayList<Transfer> getTransfersWithRecipient(Depot depot) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        try{
            LinkedList<String> rows = CSVreader.read(TRANSFER_FILEPATH, "\r\n");
            for(String row : rows){
                String[] rowdata = row.split(";");
                if(rowdata[2].equals(depot.getId().toString())) {
                    UUID readUUID = UUID.fromString(rowdata[0]);
                    Depot sender = new Depot(UUID.fromString(rowdata[1]), new Balance(0));
                    Depot receiver = new Depot(UUID.fromString(rowdata[2]), new Balance(0));
                    Amount amount = new Amount(Float.parseFloat(rowdata[3]));
                    transfers.add(new Transfer(readUUID, sender, receiver, amount));
                }
            }
            return transfers;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
