package dao.user;

import java.sql.SQLException;
import java.util.List;

import dto.OrderDetailDTO;
import dto.StoreDTO;

public interface OrderDAO {

	public StoreDTO selectStoreById(int key) throws SQLException;
	public void insertOrderDetail(List<OrderDetailDTO> list,int orderId) throws SQLException;
	public String canOrderCheck(int tableNo) throws SQLException ;
	public int findLastOrderId(int tableNo) throws SQLException ;
	public void insertNewOrder(int tableNo,int StoreId) throws SQLException ;
	public int findOrderTotalPrice(int order_id) throws SQLException ;
	public void updateOrderTotalPrice(int total_price,int order_id) throws SQLException ;
	
}
