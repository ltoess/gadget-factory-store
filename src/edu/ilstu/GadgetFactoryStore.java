package edu.ilstu;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

// driver class 
public class GadgetFactoryStore {
	
	public static final double TAX = 1.08; 
	
public static void main(String[] args) {
		
		// object instantiation
		LocalDate date = LocalDate.of(2025, 4, 1);
		Random rand = new Random();
		Deque<List<Gadget>> warehouse = new ArrayDeque<>();
		Deque<Order> waitlist = new ArrayDeque<>();
		List<Order> returnEligible = new ArrayList<>();
		
		// variable instantiation 
		int currentMonth = 0, lastMonth = 0; 
		int materialPrice = 12, updatedPrice;
		boolean newMonth = false; 
		boolean takingOrders = true;
		boolean resumeOrders = false;
		
		// finances/month
		int soldThisMonth = 0;
		int returnedThisMonth = 0;
		double salesThisMonth = 0; 
		double returnsThisMonth = 0; 
		
		// last month's stats
		double previousProfit = 0;
		double previousSales = 0;
		double previousReturns = 0;
		int previousSoldCount = 0;
		int previousReturnedCount = 0;
		int previousMaterialPrice = 12;
		
		// modifiable variables
		final int SIMULATION_RUNTIME = 100; // runtime of simulation, in days 
		final int BATCH_SIZE = 5; // max number of gadgets that each batch will hold 
		final int NUM_OF_BATCHES = 2; // max number of batches to be produced in one day
		
		// start of simulation  
		System.out.println("            Welcome to the Gadget Factory Store Simulation System");
		for (int day = 0; day < SIMULATION_RUNTIME; day++ ) {
			
			// preliminary calculations 
			// immediately make two batches for the day 			
			for (int batchNum = 0; batchNum < NUM_OF_BATCHES; batchNum++) {
				List<Gadget> batch = new ArrayList<>();
				for (int gadgetNum = 0; gadgetNum < BATCH_SIZE; gadgetNum++) {
					Gadget gad = new Gadget(materialPrice);
					batch.add(gad);
				}
				warehouse.push(batch);
			}		
			// and check to see if month changed. if changed, update materialPrice & print profit
			if (newMonth) {
							
                System.out.println("Monthly Profit Report:");
                double profit = (salesThisMonth - returnsThisMonth)/TAX - (soldThisMonth - returnedThisMonth)*materialPrice;
                System.out.printf("                     Total sales:$%.2f       gadgets sold:%d           Returned:%d             Profit:$%.2f%n", salesThisMonth, soldThisMonth, returnedThisMonth, profit);            
               
                // store last month's value in case of first-day-of-the-month returns 
                previousProfit = profit;
                previousSales = salesThisMonth;
                previousReturns = returnsThisMonth;
                previousSoldCount = soldThisMonth;
                previousReturnedCount = returnedThisMonth;
                previousMaterialPrice = materialPrice;
                
                // then reset for next month
                soldThisMonth = 0;
                returnedThisMonth = 0;
                salesThisMonth = 0;
                returnsThisMonth = 0;
                
                // update material price 
                updatedPrice = rand.nextInt(10,16);
				while(updatedPrice == materialPrice) {
					updatedPrice = rand.nextInt(10,16);
				}
				materialPrice = updatedPrice; 	          
			}	
			
			// switches orders back on 
			// two booleans must be used because we don't want to turn takingOrders back on mid-day and risk having a "taking orders" calculation occur
			// this way, if we are ready to take orders the next day, we set resumeOrders to true (while in the loop), 
			// so then takingOrders becomes true at the START of the next day, not the middle of the loop
			if(resumeOrders) {
				takingOrders = true;
				resumeOrders = false;
			}		
				
			System.out.println("Date: " + date.toString());
			System.out.println("gadgets in stock: " + countStock(warehouse));	
			
			// ***** BEGINNING OF ORDER PROCESSING *****
			// start with case where waitlist is full 
			if (takingOrders) { // condition where we are taking new orders 
				// create new order for the day 
				System.out.println("\nNew order: ");							
				int numGadgets = rand.nextInt(1,31);
				Order newOrder = new Order(numGadgets, date);
				System.out.printf("                    Order number:  %d    gadgets ordered:  %d%n", newOrder.getOrderNumber(), newOrder.getGadgets());
				
				if (countStock(warehouse) >= newOrder.getGadgets()) { // checks if there is enough stock for the order 
					System.out.println("\nProcessing the New order...");
					System.out.println("Delivering the following gadgets:");
					List<Gadget> orderStock = prepareOrder(newOrder.getGadgets(), warehouse);
					
					newOrder.fulfillOrder(orderStock, date);
					returnEligible.add(newOrder);
					soldThisMonth += orderStock.size();
					salesThisMonth += newOrder.calcTotal(TAX);
					
					for(Gadget g : orderStock) 
						System.out.printf("                    %s%n", g);		
					System.out.printf("                    %s%n", newOrder);	
					
					System.out.println("\ngadgets in stock: " + countStock(warehouse));	
					System.out.println();
				} 
				else {	// still taking orders but not enough gadgets, so adds order to waitlist queue 		
					System.out.println("\nNot enough gadgets for the order, saving it for future deliveries.");
					System.out.println();
					waitlist.addLast(newOrder);
					if (waitlist.size() >= 3) { 
						takingOrders = false;
						System.out.println("\nStop taking new orders, waiting to process the order:");
						Order waitlistOrder = waitlist.peek();
						System.out.printf("                    Order number:  %d    gadgets ordered:  %d%n", waitlistOrder.getOrderNumber(), waitlistOrder.getGadgets());
					}	
					
					if(takingOrders) {	// attempt to clear a queue order even if there is not enough stock for the newOrder
						while(!waitlist.isEmpty() && countStock(warehouse) >= waitlist.peek().getGadgets()) {
							Order waitlistOrder = waitlist.poll();
							System.out.println("Processing an old order: ");
							List<Gadget> orderStock = prepareOrder(waitlistOrder.getGadgets(), warehouse);
							
							waitlistOrder.fulfillOrder(orderStock, waitlistOrder.getOrderDate());
							returnEligible.add(waitlistOrder);
							soldThisMonth += orderStock.size();
							salesThisMonth += waitlistOrder.calcTotal(TAX);
							for(Gadget g : orderStock) 
								System.out.printf("                    %s%n", g);		
							System.out.printf("                    %s%n", waitlistOrder);
							
							System.out.println("\ngadgets in stock: " + countStock(warehouse));	
							System.out.println();
						}
					
				}
					if(takingOrders) {	// attempt to clear a queue order if enough stock remains after initial processing
						while(!waitlist.isEmpty() && countStock(warehouse) >= waitlist.peek().getGadgets()) {
							Order waitlistOrder = waitlist.poll();
							System.out.println("Processing an old order: ");
							List<Gadget> orderStock = prepareOrder(waitlistOrder.getGadgets(), warehouse);
							
							waitlistOrder.fulfillOrder(orderStock, waitlistOrder.getOrderDate());
							returnEligible.add(waitlistOrder);
							soldThisMonth += orderStock.size();
							salesThisMonth += waitlistOrder.calcTotal(TAX);
							for(Gadget g : orderStock) 
								System.out.printf("                    %s%n", g);		
							System.out.printf("                    %s%n", waitlistOrder);
							
							System.out.println("\ngadgets in stock: " + countStock(warehouse));	
							System.out.println();
						}
					}
				}																		
			} else { // condition if we are NOT taking orders (clearing the waitlist)
				if (!waitlist.isEmpty()) {
					System.out.println("Next order to process:");
					System.out.printf("                    Order number:  %d    gadgets ordered:  %d%n", waitlist.peek().getOrderNumber(), waitlist.peek().getGadgets());
					while (!waitlist.isEmpty() && countStock(warehouse) >= waitlist.peek().getGadgets()) { // same process as above but modified to accept an existing order from waitlist 
						Order currentWLOrder = waitlist.poll();
						System.out.println("Processing old order...");
						System.out.println("Delivering the following gadgets:");
	
						List<Gadget> orderStock = prepareOrder(currentWLOrder.getGadgets(), warehouse);
										
						currentWLOrder.fulfillOrder(orderStock, currentWLOrder.getOrderDate());
						returnEligible.add(currentWLOrder);
						soldThisMonth += orderStock.size();
						salesThisMonth += currentWLOrder.calcTotal(TAX);
						
						for(Gadget g : orderStock) 
							System.out.printf("                    %s%n", g);		
						System.out.printf("                    %s%n", currentWLOrder);

						System.out.println("\ngadgets in stock: " + countStock(warehouse));	
						System.out.println();
						
						
					}
					
					if(waitlist.isEmpty()) {
						System.out.println("\nStart taking new orders next day.");
	                    resumeOrders = true;
					} 
				}
			}
			
			
			// RETURN PROCESSING 
			if (!returnEligible.isEmpty() && rand.nextInt(5) == 0) {
			    Iterator<Order> it = returnEligible.iterator();   
			    while (it.hasNext()) {
			        Order o = it.next();
			        if (o.getProcessingDate().plusDays(3).isBefore(date)) // keeps iterating through until all orders older than three days are gone 
			            it.remove();			        
			    }

			    // pick one of the remaining eligible orders at random
			    if (!returnEligible.isEmpty()) {
			        int pick = rand.nextInt(returnEligible.size());
			        Order returnOrder = returnEligible.remove(pick);  		        
			        System.out.println();
			        System.out.println("Returning order:");
			        System.out.println("Order date: " + returnOrder.getOrderDate());
			        System.out.printf("                    Order number:  %d    gadgets ordered:  %d%n", returnOrder.getOrderNumber(), returnOrder.getGadgets());
			        System.out.println();
			        
			        // check if it is a cross-month return
			        if (returnOrder.getProcessingDate().getMonthValue() != date.getMonthValue()) {
			        	double netRefund = returnOrder.calcTotal(TAX) / TAX;
			        	double costOfReturn = returnOrder.getGadgets() * previousMaterialPrice;
			        	double adjustedPreviousProfit = previousProfit - (netRefund - costOfReturn);
			        	System.out.println();
			        	System.out.printf("             Last month's profit:$%.2f    Adjusted profit:$%.2f%n", previousProfit, adjustedPreviousProfit);
			        	previousProfit = adjustedPreviousProfit;
			        }
			        // update monthly return counts
			        returnedThisMonth += returnOrder.getGadgets();
			        returnsThisMonth  += returnOrder.calcTotal(TAX);
			    }
			}

			// date incrementing and determining when the month changes 
			lastMonth = date.getMonthValue();
			date = date.plusDays(1);
			currentMonth = date.getMonthValue();
			if (lastMonth != currentMonth) 
				newMonth = true; 
			else 
				newMonth = false; 		
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
	
	// method that takes the number of required gadgets and the gadget list stack as parameters, returns a list of gadgets of the specified size
	public static List<Gadget> prepareOrder(int gadgets, Deque<List<Gadget>> stock) {
		List<Gadget> stockForOrder = new ArrayList<>();
		while(stockForOrder.size() < gadgets) {	// fills stockForOrder until it has the same number of gadgets newOrder requires
			int stillNeeded = gadgets - stockForOrder.size();				
			List<Gadget> pulls = new ArrayList<>();
			pulls = stock.pop();	
				if(pulls.size() <= stillNeeded) {
					stockForOrder.addAll(pulls);
				} else { // makes a sublist of ONLY the remaining gadgets required, then loops through said sublist to fill stockForOrder
					for(Gadget g : pulls.subList(0, stillNeeded))
						stockForOrder.add(g);
					List<Gadget> remaining = new ArrayList<>(pulls.subList(stillNeeded, pulls.size()));
					stock.push(remaining);
				}		
		}
			return stockForOrder;
	}

}
