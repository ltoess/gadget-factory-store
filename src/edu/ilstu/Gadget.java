package edu.ilstu;

import java.util.Random;

public class Gadget {

	private Random rand = new Random();
	private int price; // the price is determined by the material price, which fluctuates in between [10,15] monthly  
	private int id; // a unique identifier to each gadget produced 
	
	
	public Gadget(int materialPrice) {
		// generates the price and id for the current gadget object
		this.price = materialPrice + rand.nextInt(5,11);
	}
	
	public int getPrice() {
		return price;
	}
	
	public String toString() {
		// returns the information in a formatted manner 
		return "Price: " + price + " ID: " + id; 
	}
	
	
	
}
