package edu.ilstu;

import java.time.LocalDate;
import java.util.ArrayList;
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
		Stack<Gadget> stock = new Stack<>();
	
		int currentMonth = 0; 
		int lastMonth = 0; 
		boolean newMonth = false; 
		int materialPrice = 12;
		int updatedPrice; 
		
		for (int i = 0; i < 100; i++ ) {	
			System.out.println("Today's date: " + date.toString() + newMonth + " " + materialPrice);
			
			
			// changes materialPrice depending on a new month. fluctuates between 10 & 15 monthly, starts at 12
			if (newMonth) {
				updatedPrice = rand.nextInt(10,16);
				while(updatedPrice == materialPrice) {
					updatedPrice = rand.nextInt(10,16);
				}
				materialPrice = updatedPrice; 
			}
			
		
			int numGadgets = rand.nextInt(1,31); // [1,30]
			Order newOrder = new Order(numGadgets, date);

			
			// making two batches
			List<Gadget> batch = new ArrayList<>();
			for (int batchNum = 0; batchNum < 2; batchNum++) {
				for (int j = 0; j < 5; j++) {
					Gadget gad = new Gadget(materialPrice);
					batch.add(gad);
				}
				
			}
			batches.push(batch);
						
			
			lastMonth = date.getMonthValue();
			date = date.plusDays(1);
			currentMonth = date.getMonthValue();
			if (lastMonth != currentMonth) 
				newMonth = true; 
			else 
				newMonth = false; 
			
		}
		
		
		

		
		
		
		
		
		
	}

}
