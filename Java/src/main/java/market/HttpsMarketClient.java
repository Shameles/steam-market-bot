package market;


import com.google.gson.*;
import market.contracts.MarketClient;
import market.contracts.PurchaseInfo;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;


public class HttpsMarketClient implements MarketClient {

    private  final URL getHistoryUrl;
    private final Gson gson;
    public HttpsMarketClient() throws MalformedURLException {
        getHistoryUrl =  new URL("http://market.csgo.com/history/json/");

        gson = createGsonBuilder().create();
    }


    public PurchaseInfo[] getLastPurchases() throws MarketOperationException {
        try {
            String requestResult = sendGET(getHistoryUrl);
            return this.gson.fromJson(requestResult, PurchaseInfo[].class);
        }
        catch (IOException e){
            throw new MarketOperationException("can't get orders history", e);
        }
    }

    //private
    private static String sendGET(URL url) throws IOException {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpsURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();

        } else {
            System.out.println("GET request not worked");
            return null;
        }
    }

    /**
     * создаем и настраиваем GsonBuilder
     * @return
     */
    private GsonBuilder createGsonBuilder(){
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        return builder;
    }
}
