package market.dal.hibernate;

import market.dal.contracts.DataAccessException;
import market.dal.contracts.PurchaseInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class HibernatePurchaseHistoryRepository implements market.dal.contracts.PurchaseHistoryRepository {

    private SessionFactory sessionFactory;

    public HibernatePurchaseHistoryRepository(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public void saveAll(Collection<PurchaseInfo> purchasesForSave) throws DataAccessException {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            try {
                for (PurchaseInfo purchaseInfo : purchasesForSave) {
                    session.saveOrUpdate(purchaseInfo);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new DataAccessException(e);
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
