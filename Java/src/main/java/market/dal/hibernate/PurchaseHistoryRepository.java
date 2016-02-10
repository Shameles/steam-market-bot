package market.dal.hibernate;

import market.dal.contracts.PurchaseInfo;

import java.util.Collection;


public class PurchaseHistoryRepository implements market.dal.contracts.PurchaseHistoryRepository {

    public void saveAll(Collection<PurchaseInfo> purchasesForSave) {

    }

    public Collection<PurchaseInfo> getAll() {
        return null;
    }
}
