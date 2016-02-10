package market.dal.contracts;

import java.util.Collection;

/**
 * репозиторий работы с историей покупок
 */
public interface PurchaseHistoryRepository {
    void saveAll(Collection<PurchaseInfo> purchasesForSave);
    Collection<PurchaseInfo> getAll();
}
