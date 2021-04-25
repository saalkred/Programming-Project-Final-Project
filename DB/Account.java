package DB;

public class Account {
	private int ID = 0;
	private int type;
	private String name;
	private String username;
	private String password;
	private String email;
	private String address;
	
	public Account (int ID, int type, String name, String username, String password, String email, String address) {
		this.ID = ID;
		this.type = type;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
	}
	
	public Account (int type, String name, String username, String password, String email, String address) {
		this.type = type;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
	}
	
	public Account() {
		
	}
	
	public void print() {
		System.out.println("ID: " + ID);
		System.out.print("Type: ");
		switch(type) {
		case 1: System.out.println("Customer");
				break;
		case 2: System.out.println("Clerk");
				break;
		case 3: System.out.println("Manager");
				break;
		default: System.out.println("Unknown account type");
				break;
		}
		System.out.println("Name: " + name);
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Email: " + email);
		System.out.println("Address: " + address + "\n");
	}
	
	public int getID() {
		return ID;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}
}
