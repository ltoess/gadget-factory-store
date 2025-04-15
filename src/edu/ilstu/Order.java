package edu.ilstu;

import java.time.LocalDate;
import java.util.List;

public class Order {
	
	private int gadgets; // stores number of gadgets required by the current order 
	private int orderNumber; // unique identifier to each order
	private List<Gadget> theOrder; // list of gadgets, where the size of the list should match the number of gadgets required by the order
	private LocalDate orderDate; // the date that the order can be fulfilled 
	
	
	
	public Order(int gadgets, LocalDate date) {
		// generates the number of gadgets and the order id
		this.gadgets = gadgets;
		this.orderDate = date;
	}
	
	
	public int getGadgets() {
		return gadgets;
	}
	
	public void fulfillOrder(List<Gadget> theOrder) {
		// should be called if the current order is being processed and the store has enough gadgets to cover the order 
	}
	
	public String getOrder() {]
		// returns the order information, including order number and the number of gadgets the order required
		return "Order Number: " + orderNumber + " # of gadgets: " + gadgets; 
	}
	
	public String toString() {
		// should be called after the order has been fulfilled for the purpose of displaying the order total
		
	}
	
	
}
