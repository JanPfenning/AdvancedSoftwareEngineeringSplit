import IO.OutputLogger;

public class Main {

    public static void main(String[] args) {
        OutputLogger.log(
                "Hallo und Willkommen zu Split.\n" +
                "Der CLI basierte public-viewing ledger Dienst.\n" +
                "Wie können wir dir Heute helfen?");
        ActionInterface action = new InitAction();
        action.act();
    }
}
