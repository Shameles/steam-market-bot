package jobs;

import market.HttpsMarketClient;
import market.contracts.MarketClient;
import market.MarketOperationException;
import market.contracts.PurchaseInfo;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.MalformedURLException;

public class LoadLastPurchasesJob implements Job {

    private static Logger _log = Logger.getLogger("jobs.LoadLastPurchasesJob");
    private final MarketClient marketClient;
    public LoadLastPurchasesJob() throws MalformedURLException {
            marketClient = new HttpsMarketClient();
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
