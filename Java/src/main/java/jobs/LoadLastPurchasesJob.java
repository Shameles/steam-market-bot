package jobs;

import market.client.HttpMarketClient;
import market.client.contracts.MarketClient;
import market.client.contracts.MarketOperationException;
import market.client.contracts.PurchaseInfo;
import market.dal.contracts.PurchaseHistoryRepository;
import market.dal.hibernate.HibernatePurchaseHistoryRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashSet;

/**
 * джоб по загрузке последних покупок из магазина 
 */
public class LoadLastPurchasesJob implements Job {

    private static Logger _log = Logger.getLogger("jobs.LoadLastPurchasesJob");
    private final MarketClient marketClient;
    private final PurchaseHistoryRepository historyRepository;
    private SessionFactory sessionFactory;

    public LoadLastPurchasesJob(SessionFactory sessionFactory) throws MalformedURLException {
        this.sessionFactory = sessionFactory;
        marketClient = new HttpMarketClient();
        historyRepository = new HibernatePurchaseHistoryRepository();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            PurchaseInfo[] lastPurchases = marketClient.getLastPurchases();
            Collection<market.dal.contracts.PurchaseInfo> lastPurchaseDTOs = new HashSet<market.dal.contracts.PurchaseInfo>();
            for(PurchaseInfo purchaseInfo : lastPurchases ){
                lastPurchaseDTOs.add(
                        new market.dal.contracts.PurchaseInfo(purchaseInfo.getId(),
                                purchaseInfo.getClassId(),
                                purchaseInfo.getInstanceId(),
                                purchaseInfo.getHashName(),
                                purchaseInfo.getPrice(),
                                purchaseInfo.getTime()
                            ));
            }
            Session session = sessionFactory.openSession();
            try {
                historyRepository.saveAll(session,lastPurchaseDTOs);
            }
            finally {
                if (session!=null){
                    session.close();
                }
            }


            _log.info(" Hello World! - "+lastPurchases.length);
        } catch (MarketOperationException e) {
            _log.error(e);
        }

    }
}
