package edu.ilstu;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GadgetFactoryStore {
	// driver class 
	
	public static void main(String[] args) {
		LocalDate date = LocalDate.of(2025, 4, 1);
		Random rand = new Random();
		
		System.out.println("			Welcome to the Gadget Factory Store Simulation System");
		Stack<List<Gadget>> batches = new Stack<>();
	
		
	
		
		for (int i = 0; i < 100; i++ ) {	
			System.out.println("Today's date: " + date.toString());
			int materialPrice = rand.nextInt(10,16);
			Stack<Gadget> gStack = new Stack<>();
			
			
			
			
			
			
			date = date.plusDays(1);
		}
		
		
		
		
		
		
		
		
		
		
	}

}
