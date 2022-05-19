import IO.CommandLineReader;
import IO.OutputLogger;

public class SendMoneyAction implements ActionInterface {

    TransferService service;

    SendMoneyAction(){
        this.service = new TransferService(new UserRepository(), new TransferRepository());
    }

    @Override
    public void act() {
        System.out.println("From which account do you want to send?");
        String senderDepotId = CommandLineReader.readLine();
        //System.out.println("What is your Password?");
        //String senderPass = CommandLineReader.readLine();

        System.out.println("Who should receive the Payment?");
        String receiverDepotId = CommandLineReader.readLine();

        System.out.println("How much do you wish to send?");
        String amountString = CommandLineReader.readLine();

        try {
            Amount amount = new Amount(new Money(Float.parseFloat(amountString)));
            service.sendMoney(senderDepotId, receiverDepotId, amount);
            System.out.println("Money send");
        } catch (DepotNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        } catch (InvalidBalanceException e) {
            e.printStackTrace();
        } catch (TransferOutOfMoneypoolException e) {
            e.printStackTrace();
        } catch (PersistExecption e) {
            System.out.println(e);
            OutputLogger.log("Something went wrong at transfering money, please contact your administrator");
        }
    }
}
