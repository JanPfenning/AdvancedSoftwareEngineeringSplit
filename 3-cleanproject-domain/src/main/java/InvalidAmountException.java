public class InvalidAmountException extends InvalidBalanceException {
    InvalidAmountException(String errorMessage){
        super(errorMessage);
    }
}
