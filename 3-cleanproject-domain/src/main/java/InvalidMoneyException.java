public class InvalidMoneyException extends InvalidAmountException {
    InvalidMoneyException(String errorMessage){
        super(errorMessage);
    }
}
