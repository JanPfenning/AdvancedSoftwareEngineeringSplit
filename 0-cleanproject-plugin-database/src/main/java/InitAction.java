import IO.CommandLineReader;

import java.util.HashMap;

public class InitAction implements ActionInterface {

    HashMap<Integer, ActionInterface> actions = new HashMap();

    public InitAction(){
        actions.put(1, new SendMoneyAction());
        actions.put(2, new RequestMoneyAction());
        actions.put(3, new AnalyzeLedgerAction());
        actions.put(4, new CreateAccountAction());
        actions.put(5, new ViewInvoiceAction());
        actions.put(6, new PayInvoiceAction());
        actions.put(7, new InspectUsersAction());
    }

    @Override
    public void act() {
        Integer response;
        do{
            System.out.println(
                "1) Send Money\n" +
                "2) Request Money \n" +
                "3) View History (and filter/analyze/export) \n" +
                "4) Create Account \n" +
                "5) View Invoices\n" +
                "6) Pay Invoice\n" +
                "7) Inspect Users\n" +
                "0) Quit\n"
            );
            String stringResponse = CommandLineReader.readLine();
            response = Integer.parseInt(stringResponse);
            if(response==0) break;
            actions.get(response).act();

            System.out.println("\nEnter to continue");
            String awaitKey = CommandLineReader.readLine();
            if(awaitKey.equals("quit") || awaitKey.equals("exit")) break;
        }while(true);
    }
}
