package Shopping;

import java.util.ArrayList;

import DB.Item;

public class ShoppingCart {

	private ArrayList<Item> al;
	
	public ShoppingCart() {
		al = new ArrayList<>();
	}
	
	public int getQuantity(Item item) {
		int count = 0;
		for(int i = 0; i < al.size(); i++) {
			if(al.get(i) == item) {
				count++;
			}
		}
		return count;
	}
	
	public double getTotal() {
		double totalPrice = 0;
		for(int i = 0; i < al.size(); i++) {
			totalPrice += al.get(i).getPrice();
		}
		return totalPrice;
	}
	
	public void addItem(Item item, int quantity) {
		for(int i = 0; i < quantity; i++) {
			al.add(item);
		}
	}
	
	public void removeItem(Item item) {
		al.remove(item);
	}
}