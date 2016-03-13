package market.client.contract;

import com.google.gson.annotations.SerializedName;

/**
 * Класс, содержащий информацию о вещи, выставленной на продажу.
 */
public class ItemOnSale {

    //тип приложения. значени: cs - дота2, go - cs:go
    @SerializedName("app")
    private String application;

    //текстовое описание качества предмета
    @SerializedName("i_quality")
    private String qualityDescription;

    //цвет в HEX формате. пример - D2D2D2
    @SerializedName("i_name_color")
    private String color;

    //тип предмета
    @SerializedName("i_classid")
    private long classId;

    //идентификатор инстанса предмета
    @SerializedName("i_instanceid")
    private long instanceId;

    //уникальное имя
    @SerializedName("i_market_hash_name")
    private String marketHashName;

    //имя
    @SerializedName("i_market_name")
    private String marketName;

    //цена в рублях
    @SerializedName("ui_price")
    private float price;


    //properties

    public String getQualityDescription() {
        return qualityDescription;
    }

    public String getColor() {
        return color;
    }

    public long getClassId() {
        return classId;
    }

    public long getInstanceId() {
        return instanceId;
    }

    public String getMarketHashName() {
        return marketHashName;
    }

    public String getMarketName() {
        return marketName;
    }

    public float getPrice() {
        return price;
    }

    public boolean isCSGOItem(){
        return application.equalsIgnoreCase("go");
    }
}
