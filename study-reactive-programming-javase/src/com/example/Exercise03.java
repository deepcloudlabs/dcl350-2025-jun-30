package com.example;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class Exercise03 {
	public static void main(String[] args) {
		var tradeEvents = List.of(
				new TradeEvent("orcl", 100,200),
				new TradeEvent("orcl", 101,100),
				new TradeEvent("orcl", 102,200),
				new TradeEvent("orcl", 103,100),
				new TradeEvent("orcl", 104,200)
			);
		var publisher = new SubmissionPublisher<TradeEvent>();
		var volumeMapper = new TransformProcessor<TradeEvent,RichTradeEvent>(RichTradeEvent::new);
		publisher.subscribe(volumeMapper);
		volumeMapper.subscribe(new SlowSubscriber());
		volumeMapper.subscribe(new FastSubscriber());
		tradeEvents.forEach(publisher::submit);
		publisher.close();
		try {TimeUnit.SECONDS.sleep(30);}catch (Exception e) {}
		System.err.println("Application is done!");
	}
}

class SlowSubscriber implements Flow.Subscriber<RichTradeEvent> {

	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		System.err.println("SlowSubscriber has just subscribed!");
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(RichTradeEvent event) {
		System.err.println("SlowSubscriber has just received an event: %s".formatted(event));
		try {TimeUnit.SECONDS.sleep(5);}catch (Exception e) {}
		System.err.println("SlowSubscriber has processed the event: %s".formatted(event));
		subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		System.err.println("An error has occured: %s".formatted(throwable.getMessage()));
	}

	@Override
	public void onComplete() {
		System.err.println("SlowSubscriber has been completed.");
	}
	
}

class FastSubscriber implements Flow.Subscriber<RichTradeEvent> {
	
	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		System.err.println("FastSubscriber has just subscribed!");
		this.subscription = subscription;
		subscription.request(1);
	}
	
	@Override
	public void onNext(RichTradeEvent event) {
		System.err.println("FastSubscriber has just received an event: %s".formatted(event));
		System.err.println("FastSubscriber has processed the event: %s".formatted(event));
		subscription.request(1);
	}
	
	@Override
	public void onError(Throwable throwable) {
		System.err.println("An error has occured: %s".formatted(throwable.getMessage()));
	}
	
	@Override
	public void onComplete() {
		System.err.println("FastSubscriber has been completed.");
	}
	
}