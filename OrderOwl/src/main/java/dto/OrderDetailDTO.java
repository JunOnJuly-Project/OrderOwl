package dto;

public class OrderDetailDTO {
	private int orderDetailId;
    private int orderId;
    private int menuId;
    private int quantity;
    private int price;
    
	public OrderDetailDTO(int orderDetailId, int orderId, int menuId, int quantity, int price) {
		super();
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.menuId = menuId;
		this.quantity = quantity;
		this.price = price;
	}

	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
