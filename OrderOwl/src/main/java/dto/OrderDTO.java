package dto;

import java.time.LocalDateTime;
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
    
    
}
