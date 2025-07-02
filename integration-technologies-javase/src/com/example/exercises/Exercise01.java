package com.example.exercises;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class Exercise01 {

	private static final List<String> SYMBOLS = List.of("LTCBTC", "BTCUSDT", 
			"BNTETH", "ZRXBTC","EOSBTC","ZECBTC", "MTLETH", "KMDETH",
			"HSRETH", "ICNBTC");
	private static final String BINANCE_REST_API = "https://api.binance.com/api/v3/ticker/price?symbol=%s";

	public static void main(String[] args) throws IOException, InterruptedException {
		var httpClient = HttpClient.newBuilder().build();
		var start = System.currentTimeMillis();
		for (var symbol : SYMBOLS) {
			var request = HttpRequest.newBuilder(URI.create(BINANCE_REST_API.formatted(symbol))).build();
			var response = httpClient.send(request, BodyHandlers.ofString()).body();
			System.out.println(response);
		}
		var stop = System.currentTimeMillis();
		System.out.println("Duration: %d ms.".formatted(stop-start));
	}

}
