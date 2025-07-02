package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exercise01 {

	public static void main(String[] args) {
		var service = new Service1();
		System.out.println(service.work());
		System.out.println(service.workHard());
		// observer pattern: observable -> result -- event --> observer
		service.asyncWorkHard().thenAcceptAsync(result -> {
			System.out.println("[%s] Received the result: %d".formatted(Thread.currentThread().getName(),result));
		});
		for (var i=0;i<100;++i) {
			System.out.println("[%s] Working another task: %d".formatted(Thread.currentThread().getName(),i));
			try{TimeUnit.MILLISECONDS.sleep(100);}catch(Exception e) {}
		}
	}

}

class Service1 {
	// synchronous method
	public int work() {
		return 42;
	}
	// synchronous hard-working method
	public int workHard() {
		try{TimeUnit.SECONDS.sleep(5);}catch(Exception e) {}
		return 42;
	}
	// asynchronous hard-working method
	public CompletableFuture<Integer> asyncWorkHard() {
		return CompletableFuture.supplyAsync(() -> {
			for (var i=0;i<100;++i) {
				System.out.println("[%s] Working on the hard task: %d".formatted(Thread.currentThread().getName(),i));
				try{TimeUnit.MILLISECONDS.sleep(100);}catch(Exception e) {}
			}
			return 42;			
		});
	}
}