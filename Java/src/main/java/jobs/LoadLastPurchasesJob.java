package jobs;

import market.client.HttpMarketClient;
import market.client.contracts.MarketClient;
import market.client.contracts.MarketOperationException;
import market.client.contracts.PurchaseInfo;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.MalformedURLException;

/**
 * джоб по загрузке последних покупок из магазина
 */
public class LoadLastPurchasesJob implements Job {

    private static Logger _log = Logger.getLogger("jobs.LoadLastPurchasesJob");
    private final MarketClient marketClient;
    public LoadLastPurchasesJob() throws MalformedURLException {
            marketClient = new HttpMarketClient();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            PurchaseInfo[] lastPurchases = marketClient.getLastPurchases();
            String s="";
        } catch (MarketOperationException e) {
            _log.error(e);
        }
        _log.info("Hello World! - ");
    }
}
