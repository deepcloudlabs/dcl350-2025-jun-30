package com.example;

public record RichTradeEvent(String symbol, double price, double quantity,double volume) {

	public RichTradeEvent(TradeEvent tradeEvent) {
		this(tradeEvent.symbol(),tradeEvent.price(),tradeEvent.quantity(),tradeEvent.price()*tradeEvent.quantity());
	}
}