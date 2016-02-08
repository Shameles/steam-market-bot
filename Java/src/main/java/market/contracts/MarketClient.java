package market.contracts;

import market.MarketOperationException;

/**
 * Интерфейс взаимодействия с магазином
 */
public interface MarketClient {

    /**
     * Получить список последних 50 покупок со всей торговой площадки.
     */
    PurchaseInfo[] getLastPurchases() throws MarketOperationException;
}
