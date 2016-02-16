package market.dal.hibernate;

import market.dal.contracts.PurchaseInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;


public class HibernatePurchaseHistoryRepository implements market.dal.contracts.PurchaseHistoryRepository {


    public void saveAll(Session session, Collection<PurchaseInfo> purchasesForSave) {
        Transaction transaction = session.beginTransaction();
        try {
            for (PurchaseInfo purchaseInfo : purchasesForSave ) {
                session.saveOrUpdate(purchaseInfo);
            }
            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }

    }

    public Collection<PurchaseInfo> getAll(Session session) {
        return null;
    }
}
