package com.example;

public class Exercise01 {

	public static void main(String[] args) {
		Integer x = Integer.valueOf(42);
		Integer y = 42; // Auto-boxing
		System.out.println("x==y ? "+(x==y)); // true
		Integer p = 542;
		Integer q = 542;
		System.out.println("p==q ? "+(p==q)); // false
	}

}
