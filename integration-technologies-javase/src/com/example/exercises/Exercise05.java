package com.example.exercises;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Exercise05 {

	public static void main(String[] args) {
		System.out.println("Application is just started..");
	     var businessService = new PreciousBusinessService();
	     businessService.gun().thenAcceptAsync(result -> {
	    	 System.out.println("[%s] result: %d".formatted(Thread.currentThread().getName(),result));	    	 
	     });
	     for (var i=0;i<1_000;i++) {
	    	 System.out.println("[%s] Doing something else: %d".formatted(Thread.currentThread().getName(),i));
	    	 try { TimeUnit.MILLISECONDS.sleep(100);}catch(Exception e) {}
	     }
	     System.out.println("Application is just ended..");
	}

}

class PreciousBusinessService {
	
	public int fun() {
		try { TimeUnit.SECONDS.sleep(5);}catch(Exception e) {}
		return 42;
	}
	public CompletableFuture<Integer> gun() {
		return CompletableFuture.supplyAsync(() ->{
			try { TimeUnit.SECONDS.sleep(5);}catch(Exception e) {}
			return 42;			
		});
	}
}