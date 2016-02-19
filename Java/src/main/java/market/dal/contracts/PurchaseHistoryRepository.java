package market.dal.contracts;

import java.util.Collection;

/**
 * Репозиторий работы с историей покупок
 */
public interface PurchaseHistoryRepository {
    void saveAll(Collection<PurchaseInfo> purchasesForSave) throws DataAccessException;
}
