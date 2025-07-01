package com.example;

public class Exercise02 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		for (var jobStyle : JobStyle.values())
			System.out.println("%s: %d".formatted(jobStyle.name(),jobStyle.ordinal()));
		var jobStyle1 = JobStyle.valueOf("FULL_TIME");
		// Error: var jobStyle2 = new JobStyle("FULL_TIME");
	}

}

enum JobStyle {
	FULL_TIME, PART_TIME, INTERNSHIP, FREELANCE, CONTRACTOR
}