package com.example.exercises;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class Exercise04 {

private static final String HR_WS_API = "ws://localhost:4100/hr/api/v1/hr-events";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		var listener = new HrWebSocketListener();
		HttpClient.newHttpClient()
			      .newWebSocketBuilder()
				  .buildAsync(URI.create(HR_WS_API), listener);
		TimeUnit.SECONDS.sleep(30);
	}

}

class HrWebSocketListener implements Listener {

	@Override
	public void onOpen(WebSocket webSocket) {
		System.out.println("Connected to the hr ws server.");
		Listener.super.onOpen(webSocket);
	}

	@Override
	public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
		System.out.println(data.toString());
		return Listener.super.onText(webSocket, data, last);
	}

	@Override
	public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
		System.out.println("Disconnected from the hr ws server.");
		return Listener.super.onClose(webSocket, statusCode, reason);
	}

	@Override
	public void onError(WebSocket webSocket, Throwable error) {
		System.err.println("Error has occured: %s".formatted(error.getMessage()));
		Listener.super.onError(webSocket, error);
	}
	
}