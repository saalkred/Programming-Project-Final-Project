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
	
	public Item () {
		
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
