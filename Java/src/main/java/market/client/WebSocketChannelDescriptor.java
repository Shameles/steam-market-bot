package market.client;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

/**
 * Базовый класс для обработки сообщений из сокета.
 */
public abstract class WebSocketChannelDescriptor<T> {

    private final String channelName;
    private final Gson deserializer;
    private final Class<T> persistentClass;

    public WebSocketChannelDescriptor(String channelName, Gson deserializer) {
        this.channelName = channelName;
        this.deserializer = deserializer;
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Обработка сообщения из сокета
     * @param rawChannelMessage текс сообщения, полученного из сокета
     */
    public abstract void processMessage(String rawChannelMessage);

    /**
     * Проверка на то, что сообщение относится к данному каналу
     */
    public abstract boolean isChannelMessage(String rawChannelMessage);

    /**
     * Получаем обЪект из текстового сообщения
     */
    public T deserialize(String rawChannelMessage) {
        return deserializer.fromJson(prepareToDeserialize(rawChannelMessage), persistentClass);
    }

    /**
     * @return имя канала
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Подготавливаем входящее сообщение из канала для десериализации в объект
     */
    protected String prepareToDeserialize(String rawChannelMessage) {
        return rawChannelMessage;
    }

}
