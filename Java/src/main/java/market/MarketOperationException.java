package market;


public class MarketOperationException extends Exception {
    public MarketOperationException(String message) {
        super(message);
    }

    public MarketOperationException(String message, Exception e) {
        super(message, e);
    }
}