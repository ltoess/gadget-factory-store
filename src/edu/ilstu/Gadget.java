package edu.ilstu;
 
public class Gadget {

	private int price; // the price is determined by the material price, which fluctuates in between [10,15] monthly  
	private int id; // a unique identifier to each gadget produced 
	
	
	public Gadget(int materialPrice) {
		// generates the price and id for the current gadget object
		this.price = materialPrice;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String toString() {
		// returns the information in a formatted manner 
		return "Price: " + price + " ID: " + id; 
	}
	
	
	
}
