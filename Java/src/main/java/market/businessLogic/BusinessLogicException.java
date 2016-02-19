package market.businessLogic;

/**
 * Обертка для ошибок, которые кидаются классами бизнес-логики.
 */
public class BusinessLogicException extends Exception {
    public BusinessLogicException(Throwable e) {
        super(e);
    }

    public BusinessLogicException(String message, Throwable e) {
        super(message, e);
    }
}
