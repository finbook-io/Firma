package io.finbook.controller.output;

import io.finbook.controller.websocket.ClientSocket;
import io.finbook.model.SignData;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WebSocketOutputHandler implements OutputHandler {

    private String webSocketUri;
    private WebSocketClient client;
    private final SignData signData;

    public WebSocketOutputHandler(String webSocketUri, SignData signData) {
        this.webSocketUri = webSocketUri;
        this.signData = signData;
        this.client = new WebSocketClient();
        initOutput();
    }

    @Override
    public void initOutput() {
        try {
            client.start();
        } catch (Exception ignored) {}
    }

    @Override
    public void returnTextSigned() {
        try {
            ClientSocket clientSocket = new ClientSocket(signData.getTextToSign(), signData.getSign());
            URI echoUri = new URI(webSocketUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();

            client.connect(clientSocket, echoUri, request);

            clientSocket.awaitClose(5, TimeUnit.SECONDS);
            client.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
