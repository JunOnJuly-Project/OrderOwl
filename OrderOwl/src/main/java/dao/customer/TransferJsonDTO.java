package dao.customer;

public class TransferJsonDTO {
	
	String menuName;
	int price;
	int quantity;
	
	
	
	public TransferJsonDTO(String menuName, int price, int quantity) {
		super();
		this.menuName = menuName;
		this.price = price;
		this.quantity = quantity;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
