package market.businessLogic.commands;

import market.businessLogic.BusinessLogicException;
import market.client.contracts.MarketClient;
import market.client.contracts.PurchaseInfo;
import market.dal.contracts.PurchaseHistoryRepository;
import org.apache.log4j.Logger;
import java.util.Collection;
import java.util.HashSet;

/**
 * Команда содержит логику по загрузке данных о последних сделках с площадки в базу.
 */
public class LoadLastPurchasesCommand implements Command {

    private MarketClient marketClient;
    private PurchaseHistoryRepository repository;
    private Logger _log = Logger.getLogger(LoadLastPurchasesCommand.class);

    public LoadLastPurchasesCommand(MarketClient marketClient, PurchaseHistoryRepository repository) {

        this.marketClient = marketClient;
        this.repository = repository;
    }

    public void execute() throws BusinessLogicException {
        try {
            PurchaseInfo[] lastPurchases = marketClient.getLastPurchases();
            Collection<market.dal.contracts.PurchaseInfo> lastPurchaseDTOs = new HashSet<market.dal.contracts.PurchaseInfo>();
            for (PurchaseInfo purchaseInfo : lastPurchases) {
                lastPurchaseDTOs.add(convertToDTO(purchaseInfo));
            }
            repository.saveAll(lastPurchaseDTOs);
            _log.info(lastPurchases.length+" PurchaseInfo items processed");
        }
        catch (Exception e){
            throw new BusinessLogicException(e);
        }
    }

    //private

    /**
     * конвертирует информацию о покупке, полуенную из внешней системы в DTO
     */
    private static market.dal.contracts.PurchaseInfo convertToDTO(PurchaseInfo purchaseInfo){
        return new market.dal.contracts.PurchaseInfo(purchaseInfo.getId(),
                purchaseInfo.getClassId(),
                purchaseInfo.getInstanceId(),
                purchaseInfo.getHashName(),
                purchaseInfo.getPrice(),
                purchaseInfo.getTime()
        );
    }
}
