package market.dal.contract;

import market.util.MarketItemId;

/**
 * DTO, содержащий статистику по покупкам вещи в магазине
 */
public class ItemPurchaseStatistic {

    private MarketItemId marketItemId;

    public ItemPurchaseStatistic(MarketItemId marketItemId, float averagePrice, long purchaseCount){

        this.classId = marketItemId.getClassId();
        this.instanceId = marketItemId.getInstanceId();
        this.averagePrice = averagePrice;
        this.purchaseCount = purchaseCount;
    }

    //тип предмета(характеризуется classId и instanceId)
    private long classId;
    private long instanceId;

    //средняя цена
    private final float averagePrice;
    //кол-во покупок
    private final long purchaseCount;

    public long getClassId() {
        return classId;
    }


    public long getInstanceId() {
        return instanceId;
    }

    public float getAveragePrice() {
        return averagePrice;
    }

    public long getPurchaseCount() {
        return purchaseCount;
    }

    public MarketItemId getMarketItemId() {
        if (marketItemId==null){
            marketItemId = new MarketItemId(classId,instanceId);
        }
        return marketItemId;
    }
}
