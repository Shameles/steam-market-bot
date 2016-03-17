package market.businessLogic.workflow;


import market.businessLogic.ItemEstimateResult;
import market.businessLogic.ItemOnSaleChecker;
import market.client.contract.ItemOnSale;

/**
 * Created by stepan on 12.03.16.
 */
public class BuyNewItemWorkflow {


    private final ItemOnSaleChecker itemChecker;

    public BuyNewItemWorkflow(ItemOnSaleChecker itemChecker) {
        this.itemChecker = itemChecker;
    }

    public void execute(ItemOnSale item) {
        ItemEstimateResult checkResult = itemChecker.estimate(item);
        if (checkResult == ItemEstimateResult.IGNORE) {
            return;
        }

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
