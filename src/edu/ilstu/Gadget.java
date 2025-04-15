package edu.ilstu;
 
public class Gadget {

	private int price; 
	private int id;
	
	
	public Gadget(int materialPrice) {
		this.price = materialPrice;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String toString() {
		return "Price: " + price + " ID: " + id; 
	}
	
	
	
}
