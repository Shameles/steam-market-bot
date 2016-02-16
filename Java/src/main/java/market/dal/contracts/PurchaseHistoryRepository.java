package market.dal.contracts;

import org.hibernate.Session;

import java.util.Collection;

/**
 * репозиторий работы с историей покупок
 */
public interface PurchaseHistoryRepository {
    void saveAll(Session session, Collection<PurchaseInfo> purchasesForSave);
    Collection<PurchaseInfo> getAll(Session session);
}
