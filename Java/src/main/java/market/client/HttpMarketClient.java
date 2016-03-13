package market.client;


import com.google.gson.*;
import market.client.contract.MarketClient;
import market.client.contract.MarketOperationException;
import market.client.contract.PurchaseInfo;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpMarketClient implements MarketClient {

    private static final String GET_HISTORY_URL="http://market.csgo.com/history/json/";

    private static Logger log = LogManager.getLogger(HttpMarketClient.class);
    private final URL getHistoryUrl;
    private final Gson gson;
    public HttpMarketClient() throws MalformedURLException {
        gson = JsonUtils.createGsonBuilder().create();
        getHistoryUrl = new URL(GET_HISTORY_URL);
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
        if (responseCode == HttpsURLConnection.HTTP_OK) { // success
            log.debug("GET Response Code: 200");
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
            log.warn("GET Response Code: {}. Skip reading response body.", responseCode);
            return null;
        }
    }
}
