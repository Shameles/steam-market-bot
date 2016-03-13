package market.dal.hibernate;

import market.dal.contract.DataAccessException;
import market.dal.contract.PurchaseInfo;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import java.time.Duration;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

    public @Nullable Float getAveragePrice(long classId, long instanceId, Duration significantTimeInterval) {
        Session session = sessionFactory.openSession();
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, toIntExact(-1*significantTimeInterval.toMinutes()));
            Date minActualDate = cal.getTime();
            Query query = session.createQuery("select avg(price) from PurchaseInfo where classId = :classId and instanceId=:instanceId and time>:minActualDate");
            query.setParameter("classId", classId);
            query.setParameter("instanceId", instanceId);
            query.setParameter("minActualDate", minActualDate);
            Double avgPrice = (Double) query.uniqueResult();
            if (avgPrice==null){
                return null;
            }
            return avgPrice.floatValue();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
