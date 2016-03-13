package market.dal.contract;

/**
 * Обертка для ошибок, которые кидаются dal классами.
 */
public class DataAccessException extends Exception {
    public DataAccessException(Throwable e) {
        super(e);
    }

    public DataAccessException(String message, Throwable e) {
        super(message, e);
    }
}
