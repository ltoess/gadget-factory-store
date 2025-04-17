package edu.ilstu;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;


public class GadgetFactoryStore {
	// driver class 
	
	public static void main(String[] args) {
		
		// object instantiation
		LocalDate date = LocalDate.of(2025, 4, 1);
		Random rand = new Random();
		Deque<List<Gadget>> warehouse = new ArrayDeque<>();
		Deque<Order> waitlist = new ArrayDeque<>();
		Deque<Order> returnEligible = new ArrayDeque<>();
		
		// variable instantiation 
		int currentMonth = 0, lastMonth = 0; 
		int materialPrice = 12, updatedPrice;
		int stockQty = 0;
		boolean newMonth = false; 
		
		
		// modifiable variables
		int simulationRuntime = 100; // runtime of simulation, in days 
		int batchSize = 5; // max number of gadgets that each batch will hold 
		int numOfBatches = 2; // max number of batches to be produced in one day
		
		
		// start of simulation  
		System.out.println("            Welcome to the Gadget Factory Store Simulation System");
		for (int day = 0; day < simulationRuntime; day++ ) {
			
			// preliminary calculations 
			// immediately make two batches for the day 			
			for (int batchNum = 0; batchNum < numOfBatches; batchNum++) {
				List<Gadget> batch = new ArrayList<>();
				for (int gadgetNum = 0; gadgetNum < batchSize; gadgetNum++) {
					Gadget gad = new Gadget(materialPrice);
					batch.add(gad);
				}
				warehouse.push(batch);
			}
			
			// and check to see if month changed. if changed, update materialPrice
			if (newMonth) {
				updatedPrice = rand.nextInt(10,16);
				while(updatedPrice == materialPrice) {
					updatedPrice = rand.nextInt(10,16);
				}
				materialPrice = updatedPrice; 
			}	
			
			
			// print out start-of-day header 	
			stockQty = countStock(warehouse); 
			System.out.println("Date: " + date.toString());
			System.out.println("Gadgets in stock: " + stockQty);
			
			
			// BEGINNING OF ORDER PROCESSING
			// start with case where waitlist is full 
			
			if(waitlist.size() >= 3) {
				System.out.println("proccessing waitlist orders");
				
				// first, check to make sure stock is available for waitlist order. if not, skip to tomorrow to try again 
				if (stockQty < waitlist.peek().getGadgets()) { 
					System.out.println("Not enough gadgets for the waitlist order, check stock tomorrow.");
					
				// if stock is available, fulfill waitlist order
				} else { 
					Order oldOrder = waitlist.poll();
					List<Gadget> stockForOrder = new ArrayList<>();
					while(stockForOrder.size() < oldOrder.getGadgets()) {
						List<Gadget> pulls = new ArrayList<>();
						pulls = warehouse.pop();
						for (Gadget g : pulls) {
							if (stockForOrder.size() < oldOrder.getGadgets()) {
								stockForOrder.add(g);
							} else {
								break;
							}
						}
						
						if (stockForOrder.size() == oldOrder.getGadgets() && pulls.size() > (oldOrder.getGadgets() - stockForOrder.size())) {
							List<Gadget> extraGadgets = new ArrayList<>();
							int difference = oldOrder.getGadgets() - stockForOrder.size();
							for (int i = 0; i < difference; i++) {
								extraGadgets.add(pulls.get(i));
							}
							warehouse.push(extraGadgets);
						}		
					}
					
					if (returnEligible.size() < 3)	{				
						returnEligible.offer(oldOrder);
					} else {
						returnEligible.poll();
						returnEligible.offer(oldOrder);
					}
					
					// ADD RETURNELIGIBLE LOGIC TO ALL PRODUCTION ALGORITHMS
					
					
				}	
				
			} else {
				System.out.println("New order: ");
				
				// create new order for the day 
				int numGadgets = rand.nextInt(1,31);
				Order newOrder = new Order(numGadgets, date);
				System.out.println(newOrder.getOrder());
				
				List<Gadget> stockForOrder = new ArrayList<>();
				while(stockForOrder.size() < newOrder.getGadgets()) {
					 if (warehouse.isEmpty()) {
					        System.out.println("Error: warehouse ran out of batches unexpectedly.");
					        waitlist.offer(newOrder);
					        break; 
					    }
					
					
					List<Gadget> pulls = new ArrayList<>();
					pulls = warehouse.pop();
					for (Gadget g : pulls) {
						if (stockForOrder.size() < newOrder.getGadgets()) {
							stockForOrder.add(g);
						} else {
							break;
						}
					}
					
					if (stockForOrder.size() == newOrder.getGadgets() && pulls.size() > (newOrder.getGadgets() - stockForOrder.size())) {
						List<Gadget> extraGadgets = new ArrayList<>();
						int difference = newOrder.getGadgets() - stockForOrder.size();
						for (int i = 0; i < difference; i++) {
							extraGadgets.add(pulls.get(i));
						}
						warehouse.push(extraGadgets);
					}		
				}
				
				if (returnEligible.size() < 3)	{				
					returnEligible.offer(newOrder);
				} else {
					returnEligible.poll();
					returnEligible.offer(newOrder);
				}
				
				

			}
				
				
				
				
				
				
				
				
				
				
			
			// make flags for regular production and waitlist production, flip to determine output for the day 
			
			
			
			// date incrementing and determining when the month changes 
			lastMonth = date.getMonthValue();
			date = date.plusDays(1);
			currentMonth = date.getMonthValue();
			if (lastMonth != currentMonth) 
				newMonth = true; 
			else 
				newMonth = false; 
			System.out.println();
		}

			
		
		
	}
	
	
	
	// extra method to count stock numbers 
	public static int countStock(Deque<List<Gadget>> stock) {
		int stockQty = 0; 
		for (List<Gadget> list : stock) {
			stockQty += list.size();
		}
		return stockQty; 
	}

}
