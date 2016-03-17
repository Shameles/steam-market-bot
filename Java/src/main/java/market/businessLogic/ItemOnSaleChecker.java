package market.businessLogic;

import market.client.contract.ItemOnSale;
import market.dal.contract.ItemPurchaseStatistic;
import market.dal.contract.PurchaseHistoryRepository;
import market.util.MarketItemId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс производит оценку, стоит или нет пытаться купить лот из магазина
 */
public class ItemOnSaleChecker {

    private static Logger log = LogManager.getLogger(ItemOnSaleChecker.class);

    /**
     * список вещей, которые есть смысл пытаться проверять.
     * в key лежит classId вещи из магазина, в value - instanceId
     */
    private final Set<MarketItemId> itemsWhiteList;

    /**
     * коммисия брокеру за каждую покупку
     */
    private final double brokerCommissionForPurchase = 0.1;

    /**
     * репозиторий для работы с историей покупок
     */
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    /**
     * время в секундах, по истечению которого требуется обновить данные в @itemsPurchaseStatistic
     */
    private final int refreshIntervalInSeconds;

    /**
     * статистика по покупкам предметов
     */
    private ConcurrentHashMap<MarketItemId, ItemPurchaseStatistic> itemsPurchaseStatistic = new ConcurrentHashMap<MarketItemId, ItemPurchaseStatistic>();

    /**
     * временной отрезок от текущей даты, который учитывается при рассчете статистики по покупкам
     */
    private Duration significantTimeInterval = Duration.ofDays(2);

    /**
     * Время последнего обновления @itemsPurchaseStatistic из репозитория @purchaseHistoryRepository
     */
    private LocalDateTime lastRefreshTime;


    public ItemOnSaleChecker(Set<MarketItemId> itemsWhiteList, PurchaseHistoryRepository purchaseHistoryRepository, int refreshIntervalInSeconds) {

        this.itemsWhiteList = itemsWhiteList;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.refreshIntervalInSeconds = refreshIntervalInSeconds;
        refreshItemsPurchaseStatistic();

    }


    public ItemEstimateResult estimate(ItemOnSale newItem) {
        //ленивое обновление статистики
        if (isNeedToRefreshStatistic()) {
            refreshItemsPurchaseStatistic();
        }
        if (!newItem.isCSGOItem()) {
            return ItemEstimateResult.IGNORE;
        }
        ItemPurchaseStatistic itemStat = itemsPurchaseStatistic.get(newItem.getMarketItemId());
        if (itemStat == null) {
            return ItemEstimateResult.IGNORE;
        }
        if (itemStat.getAveragePrice() <= brokerCommissionForPurchase + newItem.getPrice()) {
            return ItemEstimateResult.IGNORE;
        }
        return ItemEstimateResult.TRY_TO_BUY;
    }

    //private

    /**
     * обновить данные по статистике из бд
     */
    private void refreshItemsPurchaseStatistic() {
        for (MarketItemId id : itemsWhiteList) {
            ItemPurchaseStatistic stat = purchaseHistoryRepository.getItemPurchaseStatistic(id, significantTimeInterval);
            if (stat != null) {
                itemsPurchaseStatistic.put(id, stat);
            } else {
                itemsPurchaseStatistic.remove(id);
            }
        }
        log.info("refreshItemsPurchaseStatistic completed successfully. Statistic available for {} items", itemsPurchaseStatistic.size());
    }

    /**
     * Проверяет, надо ли обновлять статистику с момента последнего обновления.
     *
     * @return true, если с момента поледнего обновления(@lastRefreshTime) прошел интервал больше,
     * чем @refreshIntervalInSeconds.При этом в @lastRefreshTime также записывается текущее время.
     * Иначе false, изменения
     */
    private synchronized boolean isNeedToRefreshStatistic() {
        if (LocalDateTime.now().isAfter(lastRefreshTime)) {
            lastRefreshTime = LocalDateTime.now().plusSeconds(refreshIntervalInSeconds);
            return true;
        }
        return false;
    }

}
