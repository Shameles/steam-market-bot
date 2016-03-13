package market.businessLogic.command;

import market.businessLogic.BusinessLogicException;
import market.client.contract.MarketClient;
import market.client.contract.PurchaseInfo;
import market.dal.contract.PurchaseHistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;

/**
 * Команда содержит логику по загрузке данных о последних сделках с площадки в базу.
 */
public class LoadLastPurchasesCommand implements Command {

    private MarketClient marketClient;
    private PurchaseHistoryRepository repository;
    private Logger log = LogManager.getLogger(LoadLastPurchasesCommand.class);

    public LoadLastPurchasesCommand(MarketClient marketClient, PurchaseHistoryRepository repository) {

        this.marketClient = marketClient;
        this.repository = repository;
    }

    public void execute() throws BusinessLogicException {
        try {
            PurchaseInfo[] lastPurchases = marketClient.getLastPurchases();
            if (lastPurchases==null){
                log.warn("Can't get data from market client, stop current execution.");
                return;
            }
            Collection<market.dal.contract.PurchaseInfo> lastPurchaseDTOs = new HashSet<market.dal.contract.PurchaseInfo>();
            for (PurchaseInfo purchaseInfo : lastPurchases) {
                if (isNeedToSavePurchaseInfo(purchaseInfo)) {
                    lastPurchaseDTOs.add(convertToDTO(purchaseInfo));
                }
                else {
                    log.info("PurchaseInfo [name:{}, class_id:{}, instance_id:{}, price:{}] was ignored for history",
                            purchaseInfo.getHashName(),
                            purchaseInfo.getClassId(),
                            purchaseInfo.getInstanceId(),
                            purchaseInfo.getPrice());
                }
            }
            repository.saveAll(lastPurchaseDTOs);
            log.info(lastPurchases.length+" PurchaseInfo items processed");
        }
        catch (Exception e){
            throw new BusinessLogicException(e);
        }
    }

    //private

    private static  boolean isNeedToSavePurchaseInfo(PurchaseInfo purchaseInfo){
        if (purchaseInfo.getPrice()>100){
            return true;
        }
        return false;
    }

    /**
     * конвертирует информацию о покупке, полуенную из внешней системы в DTO
     */
    private static market.dal.contract.PurchaseInfo convertToDTO(PurchaseInfo purchaseInfo){
        return new market.dal.contract.PurchaseInfo(purchaseInfo.getId(),
                purchaseInfo.getClassId(),
                purchaseInfo.getInstanceId(),
                purchaseInfo.getHashName(),
                purchaseInfo.getPrice(),
                purchaseInfo.getTime()
        );
    }
}
