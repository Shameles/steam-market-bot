package market.businessLogic;

import market.dal.contract.PurchaseHistoryRepository;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by stepan on 13.03.16.
 */
public class ItemOnSaleChecker {

    private final HashMap<Long,Long> itemsWhiteList;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private ConcurrentHashMap<String, Float> itemsAveragePrices = new ConcurrentHashMap<String, Float>();
    private Duration significantTimeInterval= Duration.ofDays(2);

    public ItemOnSaleChecker(HashMap<Long,Long>  itemsWhiteList, PurchaseHistoryRepository purchaseHistoryRepository, int refreshIntervalInSeconds){

        this.itemsWhiteList = itemsWhiteList;
        this.purchaseHistoryRepository = purchaseHistoryRepository;

    }

    private void refreshAveragePrices(){
        itemsAveragePrices.clear();
        for (Map.Entry<Long, Long> itemData:itemsWhiteList.entrySet()) {
            long classId = itemData.getKey();
            long instanceId = itemData.getValue();
            Float averagePrice = purchaseHistoryRepository.getAveragePrice(classId, instanceId,significantTimeInterval);

        }

    }

}
