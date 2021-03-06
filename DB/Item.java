package DB;

public class Item {
	private String title;
	private float price;
	private String description;
	private int stock;
	
	public Item (String title, float price, String description, int stock) {
		this.title = title;
		this.price = price;
		this.description = description;
		this.stock = stock;
	}
	
	public Item (String title, int quantity) {
		this.title = title;
		stock = quantity;
	}
	
	public Item () {
		
	}
	
	public void print() {
		System.out.println("Title: " + title);
		System.out.println("Price: " + price);
		System.out.println("Description: " + description);
		System.out.println("Stock: " + stock);
	}
	
	public String getTitle() {
		return title;
	}
	
	public float getPrice() {
		return price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getStock() {
		return stock;
	}
}
