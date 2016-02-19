package jobs;

import market.businessLogic.commands.Command;

/**
 * Created by stepan on 19.02.16.
 */
public class LoadLastPurchasesJob extends AbstractCommandJob {
    public LoadLastPurchasesJob(Command jobLogic){
        super(jobLogic);
    }
}
