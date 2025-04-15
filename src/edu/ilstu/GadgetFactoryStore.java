package edu.ilstu;

import java.time.LocalDate;
import java.util.Stack;

public class GadgetFactoryStore {
	// driver class 
	
	public static void main(String[] args) {
		LocalDate date = LocalDate.of(2025, 4, 1);
		System.out.println("                Welcome to the Gadget Factory Store Simulation System");
		
		Stack<Gadget> gStack = new Stack<>();
		
		// start of loop
		
		for (int i = 0; i < 100; i++ ) {	
			
			System.out.println("Today's date: " + date.toString());
		
			date = date.plusDays(1);
		}
		
		
		
		
		
		
		
		
		
		
	}

}
