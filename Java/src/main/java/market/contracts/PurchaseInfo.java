package market.contracts;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Информация о покупке, совершенной в магазине
 */
public class PurchaseInfo {

    //тип предмета
    @SerializedName("classid")
    private long classId;

    @SerializedName("instanceid")
    private long instanceId;

    //название
    @SerializedName("hash_name")
    private String hashName;

    //цена
    @SerializedName("price")
    private float price;

    //идентификатор покупки
    @SerializedName("id")
    private long id;

    //время совершения покупки
    @SerializedName("time")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
