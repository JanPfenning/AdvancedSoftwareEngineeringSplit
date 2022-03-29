import IO.CSVreader;
import IO.CommandLineReader;

import java.util.ArrayList;
import java.util.UUID;

public class AnalyzeLedgerAction implements ActionInterface {

    TransferService service;

    AnalyzeLedgerAction(){
        this.service = new TransferService(new UserRepository(), new TransferRepository());
    }

    @Override
    public void act() {
        System.out.println("Which UUID do you want to inspect?");
        String desiredUUID = CommandLineReader.readLine();

        try{
            ArrayList<Transfer> transfers = service.analyseTransferSendings(desiredUUID);
            if(transfers != null){
                System.out.println("Folling transfers have been made");
                for(Transfer transfer: transfers){
                    System.out.println(transfer.getId()+": "+transfer.getAmount());
                }
            }else{
                System.out.println("No sendings found for this account");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            ArrayList<Transfer> transfers = service.analyseTransferRecievings(desiredUUID);
            if(transfers != null){
                System.out.println("No transfers have been received");
                for(Transfer transfer: transfers){
                    System.out.println(transfer.getId()+": "+transfer.getAmount());
                }
            }else{
                System.out.println("No transfers where recieved for this account");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
