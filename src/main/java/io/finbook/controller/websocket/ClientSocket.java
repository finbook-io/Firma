package io.finbook.controller.websocket;

import io.finbook.controller.MessageConstructor;
import io.finbook.controller.output.WebSocketOutputHandler;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class ClientSocket {

    private final CountDownLatch closeLatch;
    private Session session;
    private String textToSign;
    private byte[] textSigned;

    public ClientSocket(String textToSign, byte[] textSigned) {
        this.closeLatch = new CountDownLatch(1);
        this.textToSign = textToSign;
        this.textSigned = textSigned;
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.session = null;
        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;

        try {
            Future<Void> fut;
            fut = session.getRemote().sendStringByFuture(MessageConstructor.toJSON(textToSign, textSigned));
            fut.get(2, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        session.close(StatusCode.NORMAL, "I'm done");
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        cause.printStackTrace(System.out);
    }
}