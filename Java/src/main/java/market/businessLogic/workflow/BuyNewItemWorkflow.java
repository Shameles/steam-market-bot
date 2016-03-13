package market.businessLogic.workflow;

import com.sun.org.apache.bcel.internal.generic.RET;

/**
 * Created by stepan on 12.03.16.
 */
public class BuyNewItemWorkflow {

    public boolean isNeedToBuy(){

        return true;
    }

    public Object getAvailableWorker(){
        return null;
    }
/*
    public void execute(item){
        if (!isNeedToBuy()){
            return;
        }

        Object worker = workersPool.getAvailableWorker(item);
        if (worker == null){
            item.declineReason = all_workers_are_busy;
            repository.save(item);
            return;
        }

        boolean isPurchaseOperationWasSuccessful=false;
        try {
            worker.buyItem(item);
            buyOperationResult = true;
            item.processStatus = buyed;
        }
        catch (Exception e){
            item.processStatus = purchaseError;
        }
        if (isPurchaseOperationWasSuccessful){
            repository.save(item);
        }

    }
*/
}
