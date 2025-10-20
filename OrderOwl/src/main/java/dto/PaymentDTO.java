package dto;

import java.time.LocalDateTime;

public class PaymentDTO {
	   private int paymentId;
	    private int orderId;
	    private LocalDateTime paymentDate;
	    private int amount;
	    private String status;
}
