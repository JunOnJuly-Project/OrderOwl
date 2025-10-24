package service.customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.customer.OrderDAOImpl;
import dao.customer.TransferJsonDTO;
import dto.OrderDetailDTO;
import dto.StoreDTO;

public class OrderService {
	
	public StoreDTO selectStoreByStoreId(int key) throws SQLException {
		
		return new OrderDAOImpl().selectStoreById(key);
		
	}
	
	public void requestOrder(List list,int orderId) throws SQLException{
		List<OrderDetailDTO> orderList = new ArrayList<OrderDetailDTO>();
		int totalPrice = 0;
		for(int i = 0 ; i < list.size();i++) {
			String[] parseList = list.get(i).toString().split(",");
		
					
					
			
		orderList.add(new OrderDetailDTO(0, 1, 
				
				(int)(Float.parseFloat(parseList[4].replace("]", "").trim())),
				
				(int)(Float.parseFloat(parseList[2].replace("]", "").trim())),
				
				(int)(Float.parseFloat(parseList[3].replace("]", "").trim()))));
		
		totalPrice += (int)(Float.parseFloat(parseList[3].replace("]", "").trim()));	
		}
		new OrderDAOImpl().insertOrderDetail(orderList,orderId);
		totalPrice += new OrderDAOImpl().findOrderTotalPrice(orderId);
		new OrderDAOImpl().updateOrderTotalPrice(totalPrice, orderId);
	}
	
	public int canOrderCheck(int tableNo,int storeId) throws SQLException {
		 String checkCan = new OrderDAOImpl().canOrderCheck(tableNo);
		 int orderTableNo = new OrderDAOImpl().findLastOrderId(tableNo);
		 if(!checkCan.equals("Y")) {		 
			 new OrderDAOImpl().insertNewOrder(tableNo,storeId);
			 orderTableNo+= new OrderDAOImpl().findLastOrderId(tableNo);
		 }
	
		
		return orderTableNo;
	}
	
	public List<TransferJsonDTO> requestOrderData(int orderId) throws SQLException {
		
		
		return new OrderDAOImpl().selectOrderAllOrderByOrderId(orderId);
		
	}

}
