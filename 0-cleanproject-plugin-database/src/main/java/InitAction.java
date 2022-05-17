import IO.CommandLineReader;
import IO.OutputLogger;

import java.util.HashMap;

public class InitAction implements ActionInterface {

    HashMap<Integer, ActionInterface> actions = new HashMap();

    public InitAction(){
        actions.put(1, new SendMoneyAction());
        actions.put(2, new RequestMoneyAction());
        actions.put(3, new AnalyzeLedgerAction());
        actions.put(4, new CreateAccountAction());
        actions.put(5, new CreateMoneypoolAction());
        actions.put(6, new ViewInvoiceAction());
        actions.put(7, new PayInvoiceAction());
        actions.put(8, new InspectUsersAction());
    }

    @Override
    public void act() {
        int response;
        do{
            OutputLogger.log(
                "1) Send Money\n" +
                "2) Request Money \n" +
                "3) View History of Depot \n" +
                "4) Create Account \n" +
                "5) Create Moneypool \n" +
                "6) View Invoices\n" +
                "7) Pay Invoice\n" +
                "8) Inspect Users\n" +
                "0) Quit\n"
            );
            String stringResponse = CommandLineReader.readLine();
            try{
                response = Integer.parseInt(stringResponse);
                if(response==0) break;
                actions.get(response).act();
                OutputLogger.log("\nEnter to continue");
                String awaitKey = CommandLineReader.readLine();
                if(!awaitKey.equals("")) break;
            }catch (Exception ignored){}
        }while(true);
    }
}
