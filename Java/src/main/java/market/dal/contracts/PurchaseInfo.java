package market.dal.contracts;
import java.util.Date;

/**
 * Информация о покупке, совершенной в магазине
 */
public class PurchaseInfo {

    public PurchaseInfo(){
    }

    public PurchaseInfo(long purchaseId, long classId, long instanceId, String hashName, float price, Date time){
        setPurchaseId(purchaseId);
        setClassId(classId);
        setInstanceId(instanceId);
        setHashName(hashName);
        setPrice(price);
        setTime(time);
    }

    //идентификатор покупки, полученный из магазина.
    private long purchaseId;

    //тип предмета
    private long classId;

    private long instanceId;

    //название
    private String hashName;

    //цена
    private float price;

    //время совершения покупки
    private Date time;

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }
}
