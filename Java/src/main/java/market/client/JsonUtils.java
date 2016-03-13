package market.client;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 *  Вспомогательные методы по работе с JSON
 */
public class JsonUtils {
    /**
     * создаем и настраиваем GsonBuilder
     */
    public static GsonBuilder createGsonBuilder(){
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong()*1000);
            }
        });
        return builder;
    }

}
