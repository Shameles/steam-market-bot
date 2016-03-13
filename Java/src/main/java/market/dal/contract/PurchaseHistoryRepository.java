package market.dal.contract;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.Collection;

/**
 * Репозиторий работы с историей покупок
 */
public interface PurchaseHistoryRepository {
    void saveAll(@NonNull Collection<PurchaseInfo> purchasesForSave) throws DataAccessException;

    /**
     * Получить среднюю цену на предмет с заданными @classId и @instanceId.
     * Учитываются только значения, полученные не позднее чем, текущая дата - @significantTimeInterval
     * @param significantTimeInterval интервал времени, который учитывается в рассчете средней цены вещи
     */
    @Nullable
    Float getAveragePrice(long classId, long instanceId, @NonNull Duration significantTimeInterval);
}
