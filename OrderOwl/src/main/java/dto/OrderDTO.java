package dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
	private int orderId;
    private int storeId;
    private int tableId;
    private LocalDateTime orderDate;
    private String status;
    private int totalPrice;
    private int minPrice;
    private String tableStatus;
    List<OrderDetailDTO> orderDetail;
	public OrderDTO(int orderId, int storeId, int tableId, LocalDateTime orderDate, String status, int totalPrice,
			int minPrice, String tableStatus) {
		super();
		this.orderId = orderId;
		this.storeId = storeId;
		this.tableId = tableId;
		this.orderDate = orderDate;
		this.status = status;
		this.totalPrice = totalPrice;
		this.minPrice = minPrice;
		this.tableStatus = tableStatus;
		this.orderDetail = new ArrayList<>();
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public String getTableStatus() {
		return tableStatus;
	}
	public void setTableStatus(String tableStatus) {
		this.tableStatus = tableStatus;
	}
	public List<OrderDetailDTO> getOrderDetail() {
		return orderDetail;
	}
	public void addOrderDetail(OrderDetailDTO orderDetail) {
		this.orderDetail.add(orderDetail);
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.orderId == ((OrderDTO) obj).orderId;
	}
}
