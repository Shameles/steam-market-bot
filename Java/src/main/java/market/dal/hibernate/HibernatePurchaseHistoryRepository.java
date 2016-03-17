package market.dal.hibernate;

import market.dal.contract.DataAccessException;
import market.dal.contract.ItemPurchaseStatistic;
import market.dal.contract.PurchaseInfo;
import market.util.MarketItemId;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.*;

import java.time.Duration;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static java.lang.Math.toIntExact;

public class HibernatePurchaseHistoryRepository implements market.dal.contract.PurchaseHistoryRepository {

    private SessionFactory sessionFactory;

    public HibernatePurchaseHistoryRepository(@NonNull SessionFactory sessionFactory) {

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

    public @Nullable ItemPurchaseStatistic getItemPurchaseStatistic(MarketItemId marketItemId, Duration significantTimeInterval) {
        Session session = sessionFactory.openSession();
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, toIntExact(-1*significantTimeInterval.toMinutes()));
            Date minActualDate = cal.getTime();
            Query query = session.createQuery("select count(*),avg(price) from PurchaseInfo where classId = :classId and instanceId=:instanceId and time>:minActualDate");
            query.setParameter("classId", marketItemId.getClassId());
            query.setParameter("instanceId", marketItemId.getInstanceId());
            query.setParameter("minActualDate", minActualDate);
            Object[] queryResult = (Object[]) query.uniqueResult();
            Long purchaseCount = (Long) queryResult[0];
            if (purchaseCount == 0){
                return null;
            }
            Double avgPrice = (Double) queryResult[1];
            return new ItemPurchaseStatistic(marketItemId, avgPrice.floatValue(), purchaseCount);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
