package market.util;

/**
 * класс для хранения идентификаторов вещи из магазина
 */
public class MarketItemId {

    private final long classId;
    private final long instanceId;

    public MarketItemId(long classId, long instanceId) {

        this.classId = classId;
        this.instanceId = instanceId;
    }


    public long getInstanceId() {
        return instanceId;
    }

    public long getClassId() {
        return classId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (classId ^ (classId >>> 32));
        result = prime * result + (int) (instanceId ^ (instanceId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        // Not strictly necessary, but often a good optimization
        if (this == other)
            return true;
        if (!(other instanceof MarketItemId))
            return false;
        MarketItemId otherMarketItemId = (MarketItemId) other;
        return classId == otherMarketItemId.getClassId()
                && instanceId == otherMarketItemId.getInstanceId();
    }
}
