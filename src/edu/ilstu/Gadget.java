package edu.ilstu;

import java.util.Random;

public class Gadget {

	private Random rand = new Random();
	private int price; // the price is determined by the material price, which fluctuates in between [10,15] monthly  
	private int id = 0; // a unique identifier to each gadget produced 
	
	private static int idIncrementer = 1; // this int is static so it keeps track of the ID across ALL gadget instances
	
	public Gadget(int materialPrice) {
		// generates the price and id for the current gadget object
		this.price = materialPrice + rand.nextInt(5,11);
		this.id = idIncrementer++;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String toString() {
		// returns the information in a formatted manner 
		return "Gadget ID: " + id + "        Price: " + price;    
	}
	
	
	
}
