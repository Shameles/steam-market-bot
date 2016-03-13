package market.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

/**
 * Класс для работ с веб-сокетами торговой площадки.
 */
@WebSocket
public class MarketWebSocketClient {

    private static Logger log = LogManager.getLogger(MarketWebSocketClient.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private WebSocketChannelDescriptor[] channelDescriptors;

    public MarketWebSocketClient(WebSocketChannelDescriptor[] channelDescriptors) {
        this.channelDescriptors = channelDescriptors;
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
        for (WebSocketChannelDescriptor channelDescriptor: channelDescriptors) {
            if (!channelDescriptor.isChannelMessage(message)){
                continue;
            }
            channelDescriptor.processMessage(message);
            break;
        }
    }

    @OnWebSocketError
    public void onError(Throwable error) throws IOException {
        log.error(error);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        log.info("Connected to server");
        for (WebSocketChannelDescriptor channelDescriptor : channelDescriptors) {
            subscribeOnChannel(session, channelDescriptor.getChannelName());
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        log.info("Connection closed: code - {} , reason - {}", statusCode, reason);
        this.latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    //private

    /**
     * Подписка на канал. По факту просто отправлем сообщение с именем канала, на который хотим подписаться, серверу.
     *
     * @param session     открытая сессия соединения с сокетом
     * @param channelName имя канала, на который хотим подписаться
     */
    private static void subscribeOnChannel(@NonNull Session session, @NonNull String channelName) {
        try {
            log.info("Try to subscribe on channel with name =" + channelName);
            session.getRemote().sendString(channelName);
            log.info("Subscribed on channel with name =" + channelName);
        } catch (IOException e) {
            log.error("Can't subscribe on channel with name =" + channelName, e);
        }
    }
}