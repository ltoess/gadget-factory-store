package edu.ilstu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {
	
	private Random rand = new Random();
	private int gadgets; // stores number of gadgets required by the current order 
	private int orderNumber = 0; // unique identifier to each order
	private List<Gadget> theOrder; // list of gadgets, where the size of the list should match the number of gadgets required by the order
	private LocalDate orderDate; // the date that the order can be fulfilled 
	private LocalDate processingDate;
	
	private static int orderNumIncrementer = 1; // same idea as static int in gadget class: used to keep track of order num across ALL order instances
	
	
	public Order(int gadgets, LocalDate date) {
		// generates the number of gadgets and the order id
		this.gadgets = gadgets;
		this.orderDate = date;
		this.orderNumber = orderNumIncrementer++;
		this.theOrder = new ArrayList<>();
	}
	
	
	public int getGadgets() {
		return gadgets;
	}
	
	public int getOrderNumber() {
		return orderNumber; 
	}
	
	public void fulfillOrder(List<Gadget> theOrder, LocalDate today) {
		this.theOrder = theOrder;
		this.processingDate = today;
	}
	
	public LocalDate getOrderDate() {
		return orderDate;
	}
	
	public LocalDate getProcessingDate() {
		return processingDate;
	}
	
	public double calcTotal(double tax) {
		double subtotal = 0; 
		for(Gadget g : theOrder)
			subtotal += g.getPrice();
		
		return subtotal * tax; 
	}
	
	public String getOrder() {
		// returns the order information, including order number and the number of gadgets the order required
		return "                    Order number: " + orderNumber + "    Gadgets ordered: " + gadgets; 
	}
	
	public String toString() {
		// should be called after the order has been fulfilled for the purpose of displaying the order total
		return String.format("Order number:%-4d Order total:  $%.2f", orderNumber, calcTotal(GadgetFactoryStore.TAX));
	}
	
	
}
