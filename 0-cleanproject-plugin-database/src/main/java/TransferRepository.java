import IO.CSVwriter;

import java.io.FileNotFoundException;

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
}
