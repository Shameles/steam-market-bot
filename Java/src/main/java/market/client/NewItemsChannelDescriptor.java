package market.client;

import com.google.gson.Gson;
import market.client.contract.ItemOnSale;

/**
 * Работа с каналом выставления новых вещей на продажу.
 */
public class NewItemsChannelDescriptor extends WebSocketChannelDescriptor<ItemOnSale>
{
    public NewItemsChannelDescriptor(Gson deserializer) {
        super("newitems", deserializer);
    }

    @Override
    protected String prepareToDeserialize(String rawChannelMessage) {
        /*
        Ожидаемое сообщение имеет вид {"type":"newitem","data":"{экранированный джсон с информацией}"}.
        Подготавливаем сообщение так, чтобы на выходе у нас был валидный джсон со снятым экранированием из data.
         */
        String dataMarker = "data\":\"";
        String result = rawChannelMessage.substring(rawChannelMessage.indexOf(dataMarker)+dataMarker.length(), rawChannelMessage.length() - 2);
        result=result.replace("\\\"","\"").replace("\\\\","\\");
        return result;
    }

    @Override
    public void processMessage(String rawChannelMessage) {
        ItemOnSale newItem = deserialize(prepareToDeserialize(rawChannelMessage));
    }

    @Override
    public boolean isChannelMessage(String message) {
        return message.contains("\"type\":\"newitem\"");
    }
}
